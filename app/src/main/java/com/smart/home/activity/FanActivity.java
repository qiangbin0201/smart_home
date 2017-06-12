package com.smart.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.home.ConsumerIrManagerCompat;
import com.smart.home.R;
import com.smart.home.model.EquipData;
import com.smart.home.model.FanProtocol;
import com.smart.home.model.StateDetail;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.model.TvProtocol;
import com.smart.home.presenter.ControlPresenter;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.presenter.OperationDataPresenter;
import com.smart.home.presenter.ServerThread;
import com.smart.home.presenter.StatusPresenter;
import com.smart.home.service.ServerService;
import com.smart.home.utils.CollectionUtil;
import com.smart.home.utils.CustomDialogFactory;
import com.smart.home.utils.DateUtil;
import com.smart.home.utils.InfraredTransformUtil;
import com.smart.home.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/4/25.
 */

public class FanActivity extends BaseActivity {

    private static final String SCHEMA = "schema";

    private static final String TOOLBAR_TITLE = "风扇";

    private static final String FAN_ON = "fan_on";

    private static final String FAN_OFF = "fan_off";

    private String mSchema;

    private MyReceiver mMyRervice;

    private int current_speed;


    private static final String SWITCH_FAN_ON = "switch_fan_on";

    @BindView(R.id.iv_fan)
    ImageView ivFan;
    @BindView(R.id.iv_speed_up)
    ImageView ivSpeedUp;
    @BindView(R.id.iv_speed_down)
    ImageView ivSpeedDown;
    @BindView(R.id.tv_equip)
    TextView tvEquip;

    public static void Launch(Context context, String schema){
        Intent intent = new Intent(context, FanActivity.class);
        intent.putExtra(SCHEMA, schema);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化数据库
        EquipDataPresenter.getInstance().initDbHelp(this);
        OperationDataPresenter.getInstance().initDbHelp(this);

        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE,R.drawable.icon_more, mBarOnClickListener);
        setContentView(R.layout.activity_fan);

        ButterKnife.bind(this);

        initBroadcast();

        initKeyTone();

        initView();

    }

    private void initView() {
        mSchema = getIntent().getStringExtra(SCHEMA);
        if(mSchema != null && mSchema.equals(LOCAL_NETWORK)) {
            mStatusPresenter = StatusPresenter.getInstance(this, EQUIP_STATUS_FILE);
            boolean switch_status = mStatusPresenter.getBoolan(SWITCH_FAN_ON, false);
            initSwitch(switch_status, ivFan);
//            if (switch_status) {
//                ivFan.setImageResource(R.drawable.on);
//                isEquipOpen = true;
//            } else {
//                ivFan.setImageResource(R.drawable.off);
//                isEquipOpen = false;
//            }
        }else if(mSchema != null && mSchema.equals(SERVER)){
            initSwitch(equipOpen, ivFan);

        }
    }

    @OnClick({R.id.iv_fan, R.id.iv_speed_up, R.id.iv_speed_down})
    public void onClick(View view){
        if(isSelectEquip){
            if(isNetConnect) {
                switch (view.getId()) {
                    case R.id.iv_fan:
                        if (!isEquipOpen) {
                            communicationSchema(FanProtocol.FAN_ON, FAN_ON, current_speed, FanProtocol.INFRARED_ON);
                            isEquipOpen = true;
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_open_fan));
                        } else {
                            communicationSchema(FanProtocol.FAN_OFF, FAN_OFF, current_speed, FanProtocol.INFRARED_OFF);
                            isEquipOpen = false;
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_close_fan));
                        }

                        break;
                    case R.id.iv_speed_up:
                        if (isEquipOpen()) {
                            communicationSchema(FanProtocol.FAN_SPEED_UP, FAN_ON, current_speed++, FanProtocol.INFRARED_SPEED_UP);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_speed_up));
                        }
                        break;
                    case R.id.iv_speed_down:
                        if (isEquipOpen()) {
                            communicationSchema(FanProtocol.FAN_SPEED_DOWN, FAN_ON, current_speed--, FanProtocol.INFRARED_SPEED_DOWN);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_speed_down));

                        }
                        break;
                    default:
                        break;
                }
            }else {
                ToastUtil.showFailed(this, getString(R.string.local_net_not_connect));
            }

        }else {
            ToastUtil.showBottom(this, getString(R.string.please_select_equip));
        }

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

    //注册广播
    private void initBroadcast() {
        mMyRervice = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServerService.REGISTER_BROADCAST);
        registerReceiver(mMyRervice, filter);
    }

    private View.OnClickListener mBarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (CollectionUtil.isEmpty(mEquipPositionList)) {
                ToastUtil.showBottom(FanActivity.this, getString(R.string.please_add_equip));
            } else {
                CustomDialogFactory.showListDialog(FanActivity.this, false, DIALOG_TITLE, mEquipPositionList, mOnClickListener);
            }
        }
    };

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            tvEquip.setText(mEquipPositionList.get(i));
            List <EquipData> mSelectList = EquipDataPresenter.getInstance().queryEquipList(mEquipPositionList.get(i));
            mSelectEquipCode = mSelectList.get(0).getEquipCode();
            isSelectEquip = true;

            if (mSchema != null && mSchema.equals(LOCAL_NETWORK)) {
                checkNetWork(getBaseContext());
                ServerService.Launch(getBaseContext(), mSelectEquipCode);
            }else if(mSchema != null && mSchema.equals(SERVER)){
                checkNetWork(getBaseContext());
                initServer();
                isNetConnect = true;
            }else {
                isNetConnect = true;
            }

        }
    };

    private void initServer() {
        addSubscription(ControlPresenter.getInstance().getFanData(mSelectEquipCode, null, -1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
            initFanData(r.data);
            return;
        }, e -> {
            e.printStackTrace();

        }));
    }

    private void communicationSchema(String fanProtocol, String fanState, int speed, String infraredProtocol){
        //获得当前媒体音量用来设置按键音大小
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);

        if(mSchema != null){
            if(mSchema.equals(LOCAL_NETWORK)){
                ServerThread.sendToClient(fanProtocol);
//                TvServerService.Launch(this, mSelectEquipCode, fanProtocol);
            }else if(mSchema.equals(SERVER)){
                addSubscription(ControlPresenter.getInstance().getFanData(mSelectEquipCode, fanState, speed).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initFanData(r.data);
                    return;
                }, e -> {
                    e.printStackTrace();

                }));

            }else {
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


    private void initFanData(StateDetail stateDetail) {
        if(stateDetail != null){
            current_speed = stateDetail.speed;
            equipOpen = stateDetail.equipOpen;

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

    private void refreshUi(String receiveMessage) {
        if (receiveMessage != null) {
            if (receiveMessage.equals(TvProtocol.TV_ON)) {
                ivFan.setImageResource(R.drawable.on);
                isEquipOpen = true;
                mStatusPresenter.putBoolean(SWITCH_FAN_ON, true);
            } else if (receiveMessage.equals(TvProtocol.TV_OFF)) {
                ivFan.setImageResource(R.drawable.off);
                isEquipOpen = false;
                mStatusPresenter.putBoolean(SWITCH_FAN_ON, false);
            }
        }
    }
}
