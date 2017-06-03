package com.smart.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.ConsumerIrManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.home.R;
import com.smart.home.model.AirConditionProtocol;
import com.smart.home.model.EquipData;
import com.smart.home.model.HandlerProtocol;
import com.smart.home.model.StateDetail;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.ControlPresenter;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.presenter.ServerThread;
import com.smart.home.service.ServerService;
import com.smart.home.service.TvServerService;
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
 * Created by lenovo on 2017/4/25.
 */

public class AirConditionActivity extends BaseActivity {

    private static final String SCHEMA = "schema";

    private  static final String TOOLBAR_TITLE = "空调";

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

    public static void launch(Context context, String schema){
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
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE,R.drawable.icon_more, mBarOnClickListener);
        setContentView(R.layout.activity_air_condition);

        ButterKnife.bind(this);

        initKeyTone();

        initBroadcast();
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
    public void onClick(View view){

        //获得当前媒体音量用来设置按键音大小
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);

        if(isSelectEquip){
            if(isNetConnect) {
                switch (view.getId()) {
                    case R.id.iv_air_condition:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (!isEquipOpen) {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_ON, AIR_CONDITION_ON, mode[1], current_temp);
                            isEquipOpen = true;
                        } else {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_OFF, AIR_CONDITION_OFF, mode[1], current_temp);
                            isEquipOpen = false;
                        }
                        break;
                    case R.id.iv_temp_up:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (isEquipOpen()) {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_TEMP_UP, AIR_CONDITION_ON, mode[1], current_temp++);
                        }

                        break;
                    case R.id.iv_temp_down:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (isEquipOpen()) {
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_TEMP_DOWN, AIR_CONDITION_ON, mode[1], current_temp--);

                        }
                        break;
                    case R.id.iv_mode_up:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (isEquipOpen()) {
                            changed_mode = current_mode < 2 ? current_mode++ : 0;
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_MODE_UP, AIR_CONDITION_ON, mode[changed_mode], current_temp);
                            tvMode.setText(mode[changed_mode]);
                        }
                        break;
                    case R.id.iv_mode_down:
                        mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                        if (isEquipOpen()) {
                            changed_mode = current_mode > 0 ? current_mode-- : 2;
                            communicationSchema(AirConditionProtocol.AIR_CONDITION_MODE_DOWN, AIR_CONDITION_ON, mode[changed_mode], current_temp);
                            tvMode.setText(mode[changed_mode]);
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

    private void communicationSchema(String AirConditionProtocol, String AirConditionState, String mode, int temp){
        if(mSchema != null){
            if(mSchema.equals(LOCAL_NETWORK)){
                ServerThread.sendToClient(AirConditionProtocol);
//                ServerThread.rvHandler.sendEmptyMessage(tvProtocol);
            }else if(mSchema.equals(SERVER)){
                addSubscription(ControlPresenter.getInstance().getAirConditionData(mSelectEquipCode, AirConditionState, mode, temp).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initAirConditionData(r.data);
                    return;
                }, e -> {
                    e.printStackTrace();

                }));

            }else {
                //红外线
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
        addSubscription(ControlPresenter.getInstance().getAirConditionData(mSelectEquipCode, null, null, -1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
            initAirConditionData(r.data);
            return;
        }, e -> {
            e.printStackTrace();

        }));

    }

    private void initAirConditionData(StateDetail stateDetail) {
        if(stateDetail != null) {
            current_temp = stateDetail.air_temp;
            current_mode = stateDetail.mode;
        }

    }

    @Override
    protected void onDestroy() {
        if(list != null){
            list = null;
        }
    if(mEquipPositionList != null){
        mEquipPositionList = null;
    }
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            isNetConnect = intent.getBooleanExtra(IS_NET_CONNECT, false);
        }
    }
}
