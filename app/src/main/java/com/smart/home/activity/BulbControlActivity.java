package com.smart.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by lenovo on 2017/4/20.
 */

public class BulbControlActivity extends BaseActivity {

    private static final String TOOLBAR_TITLE = "电灯";

    private static final String DIALOG_TITLE = "请选择设备";

    private Spinner spinner;

    private String mSchema;

    private static final String SCHEMA = "schema";

    private List<EquipData> list;

    private List<String> mEquipPositionList;

    @BindView(R.id.tv_equip_position)
    TextView tvEquipPosition;
    @BindView(R.id.tv_select_equip)
    TextView tvSelectEquip;


    public static void Launch(Context context, String schema) {
        Intent intent = new Intent(context, BulbControlActivity.class);
        intent.putExtra(SCHEMA, schema);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);
        setContentView(R.layout.activity_bulb);

        //初始化数据库
        EquipDataPresenter.getInstance().initDbHelp(this);

        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.tv_select_equip, R.id.tv_equip_position})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_equip:
                if (CollectionUtil.isEmpty(mEquipPositionList)) {
                    ToastUtil.showBottom(this, getString(R.string.please_add_equip));
                } else {
                    CustomDialogFactory.showListDialog(this, false, DIALOG_TITLE, mEquipPositionList, mOnClickListener);
                }
                break;
            default:
                break;
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

    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            tvEquipPosition.setText(mEquipPositionList.get(i));
        }
    };

}


