package com.smart.home.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.smart.home.R;
import com.smart.home.adpter.EquipListAdapter;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.model.ToolbarWrapper;

/**
 * Created by qiangbin on 2017/4/23.
 */

public class MyEquipActivity extends BaseActivity {

    private TextView mTvBack;

    private TextView mTvManager;

    private ListView mListViewEquip;

    private TextView mTvDelete;

    private EquipListAdapter mEquipListAdapter;




    private static final String TOOLBAR_TITLE = "我的设备";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);
        ToolbarWrapper toolbarWrapper = getToolbarWrapper();
        mTvBack = toolbarWrapper.getLeftButton();
        mTvManager = toolbarWrapper.getRightButton();
        mTvManager.setText(R.string.equip_manager_right);
        mTvManager.setOnClickListener(mOnClickListener);

        setContentView(R.layout.activity_my_equip);

        initView();


    }

    private void initView() {
        mListViewEquip = (ListView) findViewById(R.id.list_equip);
        mTvDelete = (TextView) findViewById(R.id.tv_delete);
        mTvDelete.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}
