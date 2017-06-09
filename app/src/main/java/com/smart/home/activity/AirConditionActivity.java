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
import com.smart.home.model.AirConditionProtocol;
import com.smart.home.model.EquipData;
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

public class AirConditionActivity extends BaseActivity {

    private static final String SCHEMA = "schema";

    private static final String TOOLBAR_TITLE = "空调";

    private String[] mode = {"制冷", "自动", "制热"};

    private String mSchema;

    private List<EquipData> list;

    private static final String AIR_CONDITION_ON = "air_condition_on";

    private static final String AIR_CONDITION_OFF = "air_condition_off";

    private List<String> mEquipPositionList;

    private boolean isEquipOpen = false;

    private MyReceiver mMyRervice;

    private int current_temp;

    private int current_mode;

    private int changed_mode;

    private static final String SWITCH_AIR_ON = "switch_air_on";

    @BindView(R.id.iv_air_condition)
    ImageView ivAirCondition;
    @BindView(R.id.iv_temp_up)
    ImageView ivTempUp;
    @BindView(R.id.iv_temp_down)
    ImageView ivTempDown;
    @BindView(R.id.iv_mode_up)
    ImageView ivModeUp;
    @BindView(R.id.iv_mode_down)
    ImageView ivModeDown;
    @BindView(R.id.tv_equip)
    TextView tvEquip;
    @BindView(R.id.tv_mode)
    TextView tvMode;

    public static void launch(Context context, String schema) {
        Intent intent = new Intent(context, AirConditionActivity.class);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE, R.drawable.icon_more, mBarOnClickListener);
        setContentView(R.layout.activity_air_condition);

        ButterKnife.bind(this);

        initKeyTone();

        initBroadcast();

        initView();
    }

    private void initView() {
        if(mSchema != null && mSchema.equals(LOCAL_NETWORK) ) {
            mStatusPresenter = StatusPresenter.getInstance(this, EQUIP_STATUS_FILE);
            boolean switch_status = mStatusPresenter.getBoolan(SWITCH_AIR_ON, false);
            initSwitch(switch_status, ivAirCondition);
        }else if (mSchema != null && mSchema.equals(SERVER)){
            initSwitch(equipOpen, ivAirCondition);
        }
//        if (switch_status) {
//            ivAirCondition.setImageResource(R.drawable.on);
//            isEquipOpen = true;
//        } else {
//            ivAirCondition.setImageResource(R.drawable.off);
//            isEquipOpen = false;
//        }

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

    @OnClick({R.id.iv_air_condition, R.id.iv_temp_up, R.id.iv_temp_down, R.id.iv_mode_up, R.id.iv_mode_down})
    public void onClick(View view) {

        //获得当前媒体音量用来设置按键音大小


        if (isSelectEquip) {
            if (isNetConnect) {
                switch (view.getId()) {
                    case R.id.iv_air_condition:
                        if (!isEquipOpen) {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_ON, AIR_CONDITION_ON, mode[1], current_temp, AirConditionProtocol.INFRARED_ON);
                            isEquipOpen = true;

                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_open_airCondition));
                        } else {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_OFF, AIR_CONDITION_OFF, mode[1], current_temp, AirConditionProtocol.INFRARED_OFF);
                            isEquipOpen = false;
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_close_airCondition));
                        }
                        break;
                    case R.id.iv_temp_up:
                        if (isEquipOpen()) {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_TEMP_UP, AIR_CONDITION_ON, mode[1], current_temp++, AirConditionProtocol.INFRARED_TEMP_UP);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_temp_up));
                        }

                        break;
                    case R.id.iv_temp_down:
                        if (isEquipOpen()) {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_TEMP_DOWN, AIR_CONDITION_ON, mode[1], current_temp--, AirConditionProtocol.INFRARED_TEMP_DOWN);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_temp_down));

                        }
                        break;
                    case R.id.iv_mode_up:
                        if (isEquipOpen()) {
                            changed_mode = current_mode < 2 ? current_mode++ : 0;
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_MODE_UP, AIR_CONDITION_ON, mode[changed_mode], current_temp, AirConditionProtocol.INFRARED_MODE_UP);
                            tvMode.setText(mode[changed_mode]);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_mode_up));
                        }
                        break;
                    case R.id.iv_mode_down:
                        if (isEquipOpen()) {
                            changed_mode = current_mode > 0 ? current_mode-- : 2;
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_MODE_DOWN, AIR_CONDITION_ON, mode[changed_mode], current_temp, AirConditionProtocol.INFRARED_MODE_DOWN);
                            tvMode.setText(mode[changed_mode]);
                            long currentTime = System.currentTimeMillis();
                            OperationDataPresenter.getInstance().insertData(DateUtil.formatDateTime(currentTime, "yyyy-MM-dd HH:mm"), mSelectEquipCode, getString(R.string.data_mode_down));
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

    private void communicationSchema(String AirConditionProtocol, String AirConditionState, String mode, int temp, String infraredProtocol) {
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
        if (mSchema != null) {
            if (mSchema.equals(LOCAL_NETWORK)) {
                ServerThread.sendToClient(AirConditionProtocol);
//                ServerThread.rvHandler.sendEmptyMessage(tvProtocol);
            } else if (mSchema.equals(SERVER)) {
                addSubscription(ControlPresenter.getInstance().getAirConditionData(mSelectEquipCode, AirConditionState, mode, temp).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initAirConditionData(r.data);
                    return;
                }, e -> {
                    e.printStackTrace();

                }));

            } else {
                //红外线
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
                ToastUtil.showBottom(AirConditionActivity.this, getString(R.string.please_add_equip));
            } else {
                CustomDialogFactory.showListDialog(AirConditionActivity.this, false, DIALOG_TITLE, mEquipPositionList, mOnClickListener);
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
        addSubscription(ControlPresenter.getInstance().getAirConditionData(mSelectEquipCode, null, null, -1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
            initAirConditionData(r.data);
            return;
        }, e -> {
            e.printStackTrace();

        }));

    }

    private void initAirConditionData(StateDetail stateDetail) {
        if (stateDetail != null) {
            current_temp = stateDetail.air_temp;
            current_mode = stateDetail.mode;
            equipOpen = stateDetail.equipOpen;
        }

    }

    @Override
    protected void onDestroy() {
        if (list != null) {
            list = null;
        }
        if (mEquipPositionList != null) {
            mEquipPositionList = null;
        }
        super.onDestroy();
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
                ivAirCondition.setImageResource(R.drawable.on);
                mStatusPresenter.putBoolean(SWITCH_AIR_ON, true);
            } else if (receiveMessage.equals(TvProtocol.TV_OFF)) {
                ivAirCondition.setImageResource(R.drawable.off);
                mStatusPresenter.putBoolean(SWITCH_AIR_ON, false);
            }
        }

    }
}
