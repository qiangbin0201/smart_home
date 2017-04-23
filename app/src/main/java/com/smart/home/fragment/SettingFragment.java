package com.smart.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.home.R;
import com.smart.home.View.SettingItemView;
import com.smart.home.activity.HomeActivity;
import com.smart.home.presenter.EquipDataPresenter;

/**
 * Created by qiangbin on 2017/4/16.
 */

public class SettingFragment extends BaseFragment {

    private TextView tv_back;

    private EquipDataPresenter mEquipDataPresenter;

    private SettingItemView mViewClear, mViewVersion, mViewFeedback, mViewEquip;

    public static SettingFragment newInstance(){
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mView);
    }

    private void initView(View view) {
        tv_back = (TextView) view.findViewById(R.id.tv_back);
        tv_back.setOnClickListener(mOnClickListener);
        mViewClear = (SettingItemView) view.findViewById(R.id.view_clear);
        mViewClear.setOnClickListener(mOnClickListener);
        mViewFeedback = (SettingItemView) view.findViewById(R.id.view_feedback);
        mViewFeedback.setOnClickListener(mOnClickListener);
        mViewVersion = (SettingItemView) view.findViewById(R.id.view_version);
        mViewVersion.setOnClickListener(mOnClickListener);
        mViewEquip = (SettingItemView) view.findViewById(R.id.view_equip);
        mViewEquip.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_back:
                    onBackPressed();
                    break;
                //清理数据库的设备信息
                case R.id.view_clear:
                    mEquipDataPresenter = EquipDataPresenter.getInstance();
                    mEquipDataPresenter.initDbHelp(getActivity());
                    mEquipDataPresenter.deleteAllData();
                    break;

                case R.id.view_equip:
                    break;


                default:
                    break;
            }
        }
    };


    @Override
    public boolean onBackPressed() {
        if(isAdded()){
            ((HomeActivity) getActivity()).showTab(0);
            return true;
        }
        return super.onBackPressed();
    }
}
