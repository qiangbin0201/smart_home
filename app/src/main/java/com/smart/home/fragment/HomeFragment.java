package com.smart.home.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smart.home.R;
import com.smart.home.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/4/16.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.iv_explain)
    ImageView ivExplain;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_bulb)
    ImageView ivBulb;
    @BindView(R.id.iv_tv)
    ImageView ivTv;
    @BindView(R.id.iv_air_condition)
    ImageView ivAirContidion;
    @BindView(R.id.iv_fan)
    ImageView ivFan;



    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,container, false);
        ButterKnife.bind(this,mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.iv_explain, R.id.iv_setting, R.id.iv_bulb, R.id.iv_air_condition, R.id.iv_tv, R.id.iv_fan})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_explain:
                break;
            case R.id.iv_setting:
                ((HomeActivity) getActivity()).showTab(1);
                break;

        }

    }


}
