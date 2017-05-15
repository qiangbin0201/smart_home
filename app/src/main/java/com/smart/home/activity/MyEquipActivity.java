package com.smart.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.smart.home.R;
import com.smart.home.adpter.EquipListAdapter;
import com.smart.home.model.Equip;
import com.smart.home.model.EquipData;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.model.ToolbarWrapper;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangbin on 2017/4/23.
 */

public class MyEquipActivity extends BaseActivity {

    private TextView mTvBack;

    private TextView mTvManager;

    private ListView mListViewEquip;

    private TextView mTvDelete;

    private EquipDataPresenter mEquipDataPresenter;

    private EquipListAdapter mEquipListAdapter;
    //从数据库获得的数据列表
    private List<EquipData> mEquipDataList;

    private Equip model;

    private static final int FLUSH_LIST = 34212;
    private static final int DELETE_SUCESS = 34213;
    private static final int CLEAR_SUCESS = 34214;


    private static final String TOOLBAR_TITLE = "我的设备";

    public static void launch(Context context){
        Intent intent = new Intent(context, MyEquipActivity.class);
        context.startActivity(intent);
    }
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
        initData();

        turnManagerStatusLogic();

    }

    private void initData() {
        mEquipDataPresenter = EquipDataPresenter.getInstance();
        mEquipDataPresenter.initDbHelp(this);
        mEquipDataList = mEquipDataPresenter.queryAllData();
        mEquipListAdapter.getEquipList().clear();
        for(EquipData equipData : mEquipDataList){
            model = new Equip();
            model.equipName = equipData.getEquipName();
            model.equipPosition = equipData.getEquipPosition();
            mEquipListAdapter.getEquipList().add(model);

        }
    }

    private void initView() {
        mEquipListAdapter = new EquipListAdapter(this);
        mListViewEquip = (ListView) findViewById(R.id.list_equip);
        mListViewEquip.setAdapter(mEquipListAdapter);
        mTvDelete = (TextView) findViewById(R.id.tv_delete);
        mTvDelete.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == mTvDelete) {
                deleteEquip();

            }else if(view == mTvManager){
                boolean isManagerStauts = isManagerStatus();
                mEquipListAdapter.setIsManageStatus(!isManagerStauts);
                if(isManagerStauts){
                    mTvManager.setText(getString(R.string.equip_manager_right));
                }else {
                    mTvManager.setText(getString(R.string.cancel));
                }
                turnManagerStatusLogic();

            }
        }
    };

    private void deleteEquip() {
        List<Equip> list = mEquipListAdapter.getSelectToDeleteList();

        for(Equip equip: list){
//            EquipData mEquipData = new EquipData();
            List<EquipData> mDeleteList = mEquipDataPresenter.queryEquipList(equip.equipPosition);

            for(int i = 0; i < mDeleteList.size(); i++){
                mEquipDataPresenter.deleteByEquipData(mDeleteList.get(i));
            }

//            mEquipDataPresenter.deleteByEquipData( mEquipDataPresenter.queryEquipList(equip.equipPosition).get(0));
//            mEquipDataPresenter.queryUserList(equip.equipPosition);


//            Long ss = mEquipData.getId();

//            mEquipDataPresenter.deleteData((long) equip.itemId);
//            String str = equip.equipPosition;
//            mEquipDataPresenter.deleteByEquipName(equip.equipPosition);

        }
        flushEquipList(DELETE_SUCESS);

    }

    /**
     * 判断是否在管理状态
     *
     * @return
     */
    public boolean isManagerStatus() {
        String text = mTvManager.getText().toString();
        if (text.equals(getString(R.string.cancel))) {
            return true;
        }
        return false;
    }

    /**
     * 管理状态转换之后的逻辑
     */
    private void turnManagerStatusLogic() {
        if (!isManagerStatus()) {
            mTvBack.setVisibility(View.VISIBLE);
            mTvDelete.setVisibility(View.GONE);
            List<Equip> list = mEquipListAdapter.getSelectToDeleteList();
            for (Equip item : list) {
                item.isSelectDel = false;
            }
            list.clear();
            flushEquipList(CLEAR_SUCESS);
        } else {
            mTvBack.setVisibility(View.INVISIBLE);
            mTvDelete.setVisibility(View.VISIBLE);
        }
    }

    public void flushEquipList(int flushReson){
        if(mEquipListAdapter.getEquipList().size()>0){
            mTvManager.setVisibility(View.VISIBLE);
        }else {
            mTvManager.setVisibility(View.GONE);
            mTvDelete.setVisibility(View.GONE);
            mTvBack.setVisibility(View.VISIBLE);
        }

        switch (flushReson){
            case FLUSH_LIST:
                List<Equip> deleteList = mEquipListAdapter.getSelectToDeleteList();
                deleteList.clear();
                break;
            case DELETE_SUCESS:
                List<Equip> list = mEquipListAdapter.getSelectToDeleteList();
                mEquipListAdapter.getEquipList().removeAll(list);
                list.clear();
                ToastUtil.showSuccess(this, getString(R.string.delete_sucess));
                if(mEquipListAdapter.getEquipList().size()>0){
                    mTvManager.setVisibility(View.VISIBLE);
                }else {
                    mTvManager.setVisibility(View.GONE);
                    mTvDelete.setVisibility(View.GONE);
                    mTvBack.setVisibility(View.VISIBLE);
                }
                break;
            case CLEAR_SUCESS:
                break;
            default:
                break;

        }
        mEquipListAdapter.notifyDataSetChanged();

        updateDeleteView();
    }

    private void updateDeleteView() {
        if (mTvDelete.getVisibility() == View.VISIBLE) {
            String text = getString(R.string.delete);
            if (mEquipListAdapter.getSelectToDeleteList().size() > 0) {
                text = getString(R.string.delete)
                        + "(" + mEquipListAdapter.getSelectToDeleteList().size() + ")";
            }
            mTvDelete.setText(text);
        }
    }
}
