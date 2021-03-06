package com.smart.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.home.ConsumerIrManagerCompat;
import com.smart.home.R;
import com.smart.home.model.EquipData;
import com.smart.home.model.HandlerProtocol;
import com.smart.home.model.StateDetail;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.model.TvProtocol;
import com.smart.home.presenter.ControlPresenter;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.presenter.InfraredPresenter;
import com.smart.home.presenter.OperationDataPresenter;
import com.smart.home.presenter.ServerThread;
import com.smart.home.presenter.StatusPresenter;
import com.smart.home.service.ServerService;
import com.smart.home.service.TvServerService;
import com.smart.home.utils.CollectionUtil;
import com.smart.home.utils.CustomDialogFactory;
import com.smart.home.utils.DateUtil;
import com.smart.home.utils.InfraredTransformUtil;
import com.smart.home.utils.NetWorkUtil;
import com.smart.home.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by qiangbin on 2017/4/25.
 */

public class TvControlActivity extends BaseActivity {

    private static final String SCHEMA = "schema";

    private static final String TOOLBAR_TITLE = "电视";

    private static final String TV_ON = "tv_on";

    private static final String TV_OFF = "tv_off";

    private static final String SWITCH_TV_ON = "switch_tv_on";

    private String mSchema;

    private MyReceiver mMyRervice;


    private List<EquipData> list;

    private List<String> mEquipPositionList;

    private int current_channel;

    private int current_volume;

    private String patternStr = "S 1 0 1 0 C";

    @BindView(R.id.tv_equip)
    TextView tvEquip;
    @BindView(R.id.iv_tv_off)
    ImageView ivTvOff;
    @BindView(R.id.iv_sound_up)
    ImageView ivSoundUp;
    @BindView(R.id.iv_sound_down)
    ImageView ivSoundDown;
    @BindView(R.id.iv_channel_up)
    ImageView ivChannelUp;
    @BindView(R.id.iv_channel_down)
    ImageView ivChannelDown;


    public static void launch(Context context, String schema) {
        Intent intent = new Intent(context, TvControlActivity.class);
        intent.putExtra(SCHEMA, schema);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mSchema = getIntent().getStringExtra(SCHEMA);
//        if(mSchema.equals(INFRARED)){
//
//        }
        EquipDataPresenter.getInstance().initDbHelp(this);
        OperationDataPresenter.getInstance().initDbHelp(this);
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE, R.drawable.icon_more, mBarOnClickListener);
        setContentView(R.layout.activity_tv);

        initBroadcast();

        initKeyTone();

        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mSchema = getIntent().getStringExtra(SCHEMA);
        if(mSchema != null && mSchema.equals(LOCAL_NETWORK)) {
            //初始化SharedPreferences的操作类
            mStatusPresenter = StatusPresenter.getInstance(this, EQUIP_STATUS_FILE);
            boolean switch_status = mStatusPresenter.getBoolan(SWITCH_TV_ON, false);
            initSwitch(switch_status, ivTvOff);
        }else if(mSchema != null && mSchema.equals(SERVER)){
            initSwitch(equipOpen, ivTvOff);
        }

    }


    @OnClick({R.id.iv_tv_off, R.id.iv_sound_up, R.id.iv_sound_down, R.id.iv_channel_up, R.id.iv_channel_down})
    public void onClick(View view) {
        if (isSelectEquip) {
            if (isNetConnect) {
                switch (view.getId()) {
                    case R.id.iv_tv_off:
                        if (!isEquipOpen) {
                            communicationSchema(TvProtocol.TV_ON, TV_ON, current_channel, current_volume, TvProtocol.INFRARED_ON);
                            isEquipOpen = true;

                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_open_tv));
                        } else {
                            communicationSchema(TvProtocol.TV_OFF, TV_OFF, current_channel, current_volume, TvProtocol.INFRARED_OFF);
                            isEquipOpen = false;

                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_close_tv));

                        }

                        break;
                    case R.id.iv_sound_up:
                        if (isEquipOpen()) {
                            communicationSchema(TvProtocol.TV_SOUND_UP, TV_ON, current_channel, current_volume++, TvProtocol.INFRARED_SOUND_UP);

                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_sound_up));
                        }
                        break;
                    case R.id.iv_sound_down:
                        if (isEquipOpen()) {
                            communicationSchema(TvProtocol.TV_SOUND_DOWN, TV_ON, current_channel, current_volume--, TvProtocol.INFRARED_SOUND_DOWN);

                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_sound_down));
                        }
                        break;
                    case R.id.iv_channel_up:
                        if (isEquipOpen()) {
                            communicationSchema(TvProtocol.TV_CHANNEL_UP, TV_ON, current_channel++, current_volume, TvProtocol.INFRARED_CHANNEL_UP);

                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_channel_up));
                        }
                        break;
                    case R.id.iv_channel_down:

                        if (isEquipOpen()) {
                            communicationSchema(TvProtocol.TV_CHANNEL_DOWN, TV_ON, current_channel--, current_volume, TvProtocol.INFRARED_CHANNEL_DOWN);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_channel_down));
                        }
                        break;
                    default:
                        break;
                }
            } else {
                ToastUtil.showFailed(this, getString(R.string.local_net_not_connect));
            }
        } else {
            ToastUtil.showBottom(this, getString(R.string.please_select_equip));
        }
    }

    //注册广播
    private void initBroadcast() {
        mMyRervice = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServerService.REGISTER_BROADCAST);
        registerReceiver(mMyRervice, filter);
    }

    private void initData() {
        mSchema = getIntent().getStringExtra(SCHEMA);
        mEquipPositionList = new ArrayList<>();
        mEquipPositionList.clear();
        list = EquipDataPresenter.getInstance().queryUserList(TOOLBAR_TITLE);
        for (EquipData equipData : list) {
            mEquipPositionList.add(equipData.getEquipPosition());
        }
    }


    private void communicationSchema(String tvProtocol, String TvState, int channel, int volume, String infraredProtocol) {
        //获得当前媒体音量用来设置按键音大小
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
        if (mSchema != null) {
            if (mSchema.equals(LOCAL_NETWORK)) {
                ServerThread.sendToClient(tvProtocol);
//                ServerThread.rvHandler.sendEmptyMessage(tvProtocol);
            } else if (mSchema.equals(SERVER)) {
                addSubscription(ControlPresenter.getInstance().getTvData(mSelectEquipCode, TvState, channel, volume).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initTvData(r.data);
                    return;
                }, e -> {
                    e.printStackTrace();

                }));

            } else {
                //红外线
                boolean hasInfrared = checkInfrared(this);
                if (hasInfrared) {
                    int[] pattern = InfraredTransformUtil.hex2time(infraredProtocol);
                    ConsumerIrManagerCompat.getInstance(this).transmit(3400, pattern);
                } else {
                    ToastUtil.showFailed(this, getString(R.string.device_no_infrared_function));
                }
            }
        }
    }


    private View.OnClickListener mBarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (CollectionUtil.isEmpty(mEquipPositionList)) {
                ToastUtil.showBottom(TvControlActivity.this, getString(R.string.please_add_equip));
            } else {
                CustomDialogFactory.showListDialog(TvControlActivity.this, false, DIALOG_TITLE, mEquipPositionList, mOnClickListener);
            }
        }
    };

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            tvEquip.setText(mEquipPositionList.get(i));
            List<EquipData> mSelectList = EquipDataPresenter.getInstance().queryEquipList(mEquipPositionList.get(i));
            mSelectEquipCode = mSelectList.get(0).getEquipCode();
            isSelectEquip = true;

            if (mSchema != null && mSchema.equals(LOCAL_NETWORK)) {
                checkNetWork(getBaseContext());
                ServerService.Launch(getBaseContext(), mSelectEquipCode);
            } else if (mSchema != null && mSchema.equals(SERVER)) {
                checkNetWork(getBaseContext());
                initServer();
                isNetConnect = true;
            } else {
                isNetConnect = true;
            }

        }


    };

    private void initServer() {
        addSubscription(ControlPresenter.getInstance().getTvData(mSelectEquipCode, null, -1, -1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
            initTvData(r.data);
            return;
        }, e -> {
            e.printStackTrace();

        }));
    }

    private void initTvData(StateDetail stateDetail) {
        current_channel = stateDetail.tv_channel;
        current_volume = stateDetail.tv_volume;
        equipOpen = stateDetail.equipOpen;

    }

    private void refreshUi(String receiveMessage) {
        if (receiveMessage != null) {
            if (receiveMessage.equals(TvProtocol.TV_ON)) {
                ivTvOff.setImageResource(R.drawable.on);
                isEquipOpen = true;
                mStatusPresenter.putBoolean(SWITCH_TV_ON, true);
            } else if (receiveMessage.equals(TvProtocol.TV_OFF)) {
                ivTvOff.setImageResource(R.drawable.off);
                isEquipOpen = false;
                mStatusPresenter.putBoolean(SWITCH_TV_ON, false);
            }
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isNetConnect = intent.getBooleanExtra(IS_NET_CONNECT, false);
            isControlSuccess = intent.getBooleanExtra(IS_CONTROL_SUCCESS, false);
            if (isControlSuccess) {
                receiveMessage = intent.getStringExtra(RECEIVE_MESSAGE);
                refreshUi(receiveMessage);
            }
        }
    }
}


