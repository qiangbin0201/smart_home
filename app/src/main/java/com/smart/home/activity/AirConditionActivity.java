package com.smart.home.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.smart.home.R;
import com.smart.home.model.EquipData;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.utils.CollectionUtil;
import com.smart.home.utils.CustomDialogFactory;
import com.smart.home.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/25.
 */

public class AirConditionActivity extends BaseActivity {

    private static final String SCHEMA = "schema";

    private  static final String TOOLBAR_TITLE = "空调";

    private String mSchema;

    private List<EquipData> list;

    private List<String> mEquipPositionList;



    @Override
    protected void onStart() {
        super.onStart();
        //初始化数据库
        EquipDataPresenter.getInstance().initDbHelp(this);

        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE,R.drawable.icon_more, mBarOnClickListener);
        setContentView(R.layout.activity_air_condition);

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
        }
    };
}