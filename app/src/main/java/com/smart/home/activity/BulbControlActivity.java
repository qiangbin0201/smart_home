package com.smart.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import com.smart.home.R;
import com.smart.home.model.BulbProtocol;
import com.smart.home.model.EquipData;
import com.smart.home.model.StateDetail;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.ControlPresenter;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.presenter.InfraredPresenter;
import com.smart.home.presenter.ServerThread;
import com.smart.home.presenter.StatusPresenter;
import com.smart.home.service.ServerService;
import com.smart.home.utils.CollectionUtil;
import com.smart.home.utils.CustomDialogFactory;
import com.smart.home.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by lenovo on 2017/4/20.
 */

public class BulbControlActivity extends BaseActivity {

    private static final String TOOLBAR_TITLE = "电灯";

    private Spinner spinner;

    private String mSchema;

    private int current_brightness;

    private static final String SCHEMA = "schema";

    private static final String BULB_OFF = "bulb_off";

    private static final String BULB_ON = "bulb_on";

    private static final String SWITCH_BULB_ON = "switch_bulb_on";

    private String mSelectEquipCode;

    private MyReceiver mMyRervice;

    public static Handler mUiHandler;


    @BindView(R.id.tv_equip)
    TextView tvEquip;
    @BindView(R.id.iv_brightness_up)
    ImageView ivBrightnessUp;
    @BindView(R.id.iv_brightness_down)
    ImageView ivBrightnessDown;
    @BindView(R.id.iv_bulb)
    ImageView ivBulb;

    public static void Launch(Context context, String schema) {
        Intent intent = new Intent(context, BulbControlActivity.class);
        intent.putExtra(SCHEMA, schema);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化数据库
        EquipDataPresenter.getInstance().initDbHelp(this);

        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE, R.drawable.icon_more, mBarOnclickListener);
        setContentView(R.layout.activity_bulb);

        initBroadcast();
        initKeyTone();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //初始化SharedPreferences的操作类
        mStatusPresenter = StatusPresenter.getInstance(this, EQUIP_STATUS_FILE);
        boolean switch_status = mStatusPresenter.getBoolan(SWITCH_BULB_ON, false);
        if(switch_status){
            ivBulb.setImageResource(R.drawable.bulb_on);
            isEquipOpen = true;
        }else {
            ivBulb.setImageResource(R.drawable.bulb_off);
            isEquipOpen = false;
        }
    }

    //注册广播
    private void initBroadcast() {
        mMyRervice = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServerService.REGISTER_BROADCAST);
        registerReceiver(mMyRervice, filter);
    }

    @OnClick({R.id.iv_bulb, R.id.iv_brightness_up, R.id.iv_brightness_down})
    public void onClick(View view) {
        //获得当前媒体音量用来设置按键音大小
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (isSelectEquip) {
            if(isNetConnect) {
                switch (view.getId()) {
                    case R.id.iv_bulb:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (!isEquipOpen) {
                            ivBulb.setImageResource(R.drawable.bulb_on);
                            isEquipOpen = true;

                            communicationSchema(BulbProtocol.BULB_ON, BULB_ON, current_brightness++);
                        } else {
                            ivBulb.setImageResource(R.drawable.bulb_off);
                            isEquipOpen = false;

                            communicationSchema(BulbProtocol.BULB_OFF, null, -1);
                        }
                        break;
                    case R.id.iv_brightness_up:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (isEquipOpen()) {
                            communicationSchema(BulbProtocol.BRIGHTNESS_UP, BULB_ON, current_brightness++);
                        }
                        break;

                    case R.id.iv_brightness_down:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (isEquipOpen()) {
                            communicationSchema(BulbProtocol.BRIGHTNESS_DOWN, BULB_ON, current_brightness--);
                        }
                        break;
                    default:
                        break;
                }
            }else {
                ToastUtil.showFailed(this, getString(R.string.local_net_not_connect));
            }
        } else {
            ToastUtil.showBottom(this, getString(R.string.please_select_equip));
        }

    }

    private View.OnClickListener mBarOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (CollectionUtil.isEmpty(mEquipPositionList)) {
                ToastUtil.showBottom(BulbControlActivity.this, getString(R.string.please_add_equip));
            } else {
                CustomDialogFactory.showListDialog(BulbControlActivity.this, false, DIALOG_TITLE, mEquipPositionList, mOnClickListener);
            }
        }
    };

    private void initData() {
        mSchema = getIntent().getStringExtra(SCHEMA);
//        if (mSchema != null && mSchema.equals(LOCAL_NETWORK)) {
//            ServerService.Launch(this, mSelectEquipCode);
//        }
        mEquipPositionList = new ArrayList<>();
        mEquipPositionList.clear();
        list = EquipDataPresenter.getInstance().queryUserList(TOOLBAR_TITLE);
        for (EquipData equipData : list) {
            mEquipPositionList.add(equipData.getEquipPosition());
        }

    }

    private void communicationSchema(String bulbProtocol, String bulbState, int brightness) {
        if (mSchema != null) {
            if (mSchema.equals(LOCAL_NETWORK)) {
                ServerThread.sendToClient(bulbProtocol);
            } else if (mSchema.equals(SERVER)) {
                addSubscription(ControlPresenter.getInstance().getBulbData(mSelectEquipCode, bulbState, brightness)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                            initBulbData(r.data);
                            return;
                        }, e -> {
                            e.printStackTrace();

                        }));

            } else {
                //红外线

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    InfraredPresenter.getInstance(getBaseContext()).transmit(38000, null);
                }
            }
        }
    }



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
        addSubscription(ControlPresenter.getInstance().getBulbData(mSelectEquipCode, null, -1)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initBulbData(r.data);
                    return;
                }, e -> {
                    e.printStackTrace();

                }));
    }

    private void initBulbData(StateDetail stateDetail) {
        current_brightness = stateDetail.brightness;

    }
    private void refreshUi(String message){
        if(message.equals(BulbProtocol.BULB_ON)){
            ivBulb.setImageResource(R.drawable.bulb_on);
            mStatusPresenter.putBoolean(SWITCH_BULB_ON, true);
        }else if(message.equals(BulbProtocol.BULB_OFF)){
            ivBulb.setImageResource(R.drawable.bulb_off);
            mStatusPresenter.putBoolean(SWITCH_BULB_ON, false);
        }
        //其他UI刷新
    }

    @Override
    protected void onDestroy() {
        //销毁连接的socket客户端
        ServerThread.killSocket();
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            isNetConnect = intent.getBooleanExtra(IS_NET_CONNECT, false);
            isControlSuccess = intent.getBooleanExtra(IS_CONTROL_SUCCESS, false);
            if(isControlSuccess) {
                receiveMessage = intent.getStringExtra(RECEIVE_MESSAGE);
                refreshUi(receiveMessage);
            }
        }
    }
}


