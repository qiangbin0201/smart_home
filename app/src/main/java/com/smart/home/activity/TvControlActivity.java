package com.smart.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.home.R;
import com.smart.home.model.EquipData;
import com.smart.home.model.StateDetail;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.model.TvProtocol;
import com.smart.home.presenter.ControlPresenter;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.service.BulbServerService;
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
 * Created by qiangbin on 2017/4/25.
 */

public class TvControlActivity extends BaseActivity {

    private static final String SCHEMA = "schema";

    private static final String TOOLBAR_TITLE = "电视";

    private static final String TV_ON = "tv_on";

    private static final String TV_OFF = "tv_off";

    private String mSchema;


    private List<EquipData> list;

    private List<String> mEquipPositionList;

    private int current_channel;

    private int current_volume;


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




    public static void launch(Context context, String schema){
        Intent intent = new Intent(context, TvControlActivity.class);
        intent.putExtra(SCHEMA,schema);
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
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE,R.drawable.icon_more, mBarOnClickListener);
        setContentView(R.layout.activity_tv);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_tv_off, R.id.iv_sound_up, R.id.iv_sound_down, R.id.iv_channel_up, R.id.iv_channel_down})
    public void onClick(View view){
        if(isSelectEquip) {
            switch (view.getId()) {
                case R.id.iv_tv_off:
                    if(!isEquipOpen) {
                        communicationSchema(TvProtocol.TV_ON, TV_ON, current_channel, current_volume);
                        isEquipOpen = true;
                    }else {

                        communicationSchema(TvProtocol.TV_OFF, TV_OFF, current_channel, current_volume);
                        isEquipOpen = false;

                    }

                    break;
                case R.id.iv_sound_up:
                    if (isEquipOpen()) {
                        communicationSchema(TvProtocol.TV_SOUND_UP, TV_ON, current_channel, current_volume++);
                    }
                    break;
                case R.id.iv_sound_down:
                    if(isEquipOpen()) {
                        communicationSchema(TvProtocol.TV_SOUND_DOWN, TV_ON, current_channel, current_volume--);
                    }
                    break;
                case R.id.iv_channel_up:
                    if(isEquipOpen()) {
                        communicationSchema(TvProtocol.TV_CHANNEL_UP, TV_ON, current_channel++, current_volume);
                    }
                    break;
                case R.id.iv_channel_down:
                    if(isEquipOpen()) {
                        communicationSchema(TvProtocol.TV_CHANNEL_DOWN, TV_ON, current_channel--, current_volume);
                    }
                    break;
                default:
                    break;
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

    private void communicationSchema(String tvProtocol, String TvState, int channel, int volume){
        if(mSchema != null){
            if(mSchema.equals(LOCAL_NETWORK)){
                TvServerService.Launch(this, mSelectEquipCode, tvProtocol);
            }else if(mSchema.equals(SERVER)){
                addSubscription(ControlPresenter.getInstance().getTvData(mSelectEquipCode, TvState, channel, volume).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                    initTvData(r.data);
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
            List <EquipData> mSelectList = EquipDataPresenter.getInstance().queryEquipList(mEquipPositionList.get(i));
            mSelectEquipCode = mSelectList.get(0).getEquipCode();

            isSelectEquip = true;
            addSubscription(ControlPresenter.getInstance().getTvData(mSelectEquipCode, null, -1, -1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                initTvData(r.data);
                return;
            }, e -> {
                e.printStackTrace();

            }));
        }


    };

    private void initTvData(StateDetail stateDetail) {
        current_channel = stateDetail.tv_channel;
        current_volume = stateDetail.tv_volume;

    }


}
