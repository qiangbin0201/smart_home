package com.smart.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.smart.home.service.BulbServerService;
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

    private  static final String TOOLBAR_TITLE = "电灯";

    private Spinner spinner;

    private String mSchema;

    private int current_brightness;

    private boolean isBulbOpen = false;

    private static final String SCHEMA = "schema";

    private static final String BULB_OFF = "bulb_off";

    private static final String BULB_ON = "bulb_on";


    private List<EquipData> list;

    private List<String> mEquipPositionList;

    private String mSelectEquipCode;

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
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE,R.drawable.icon_more, mBarOnclickListener);
        setContentView(R.layout.activity_bulb);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_bulb, R.id.iv_brightness_up, R.id.iv_brightness_down})
    public void onClick(View view) {
        if(isSelectEquip) {
            switch (view.getId()) {
                case R.id.iv_bulb:
                    if (!isBulbOpen) {
                        ivBulb.setImageResource(R.drawable.bulb_on);
                        isBulbOpen = true;

                        communicationSchema(BulbProtocol.BULB_ON, BULB_ON, current_brightness++);
                    } else {
                        ivBulb.setImageResource(R.drawable.bulb_off);
                        isBulbOpen = false;

                        communicationSchema(BulbProtocol.BULB_OFF, null, -1);
                    }
                    break;
                case R.id.iv_brightness_up:
                    if (isBulbOpen()) {
                        communicationSchema(BulbProtocol.BRIGHTNESS_UP, BULB_ON, current_brightness++);
                    }
                    break;

                case R.id.iv_brightness_down:
//                    if (!isBulbOff) {
//                        communicationSchema(BulbProtocol.BRIGHTNESS_DOWN, BULB_ON, current_brightness--);
//                    } else {
//                        ToastUtil.showBottom(this, getString(R.string.please_open_bulb));
//                    }
                    if (isBulbOpen()){
                        communicationSchema(BulbProtocol.BRIGHTNESS_DOWN, BULB_ON, current_brightness--);
                    }
                    break;
                default:
                    break;
            }
        }
        ToastUtil.showBottom(this, getString(R.string.please_select_equip));

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

        mEquipPositionList = new ArrayList<>();
        mEquipPositionList.clear();
        list = EquipDataPresenter.getInstance().queryUserList(TOOLBAR_TITLE);
        for (EquipData equipData : list) {
            mEquipPositionList.add(equipData.getEquipPosition());
        }

    }

    private void communicationSchema(String bulbProtocol, String bulbState, int brightness){
        if(mSchema != null){
            if(mSchema.equals(LOCAL_NETWORK)){
                BulbServerService.Launch(this, mSelectEquipCode, bulbProtocol);
            }else if(mSchema.equals(SERVER)) {
                addSubscription(ControlPresenter.getInstance().getBulbData(mSelectEquipCode, bulbState, brightness).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initBulbData(r.data);
                    return;
                }, e -> {
                    e.printStackTrace();

                }));

            }else {
                //红外线
            }
        }
    }

    //判断电灯是否打开
    private boolean isBulbOpen(){
        if(!isBulbOpen){
            ToastUtil.showBottom(this, getString(R.string.please_open_bulb));
            return false;
        }
        return true;
    }

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            tvEquip.setText(mEquipPositionList.get(i));
            List <EquipData> mSelectList = EquipDataPresenter.getInstance().queryEquipList(mEquipPositionList.get(i));
            mSelectEquipCode = mSelectList.get(0).getEquipCode();
            isSelectEquip = true;

            addSubscription(ControlPresenter.getInstance().getBulbData(mSelectEquipCode, null, -1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                initBulbData(r.data);
                return;
            }, e -> {
                e.printStackTrace();

            }));
        }
    };

    private void initBulbData(StateDetail stateDetail){
        current_brightness = stateDetail.brightness;

    }

}


