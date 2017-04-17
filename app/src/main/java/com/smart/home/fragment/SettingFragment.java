package com.smart.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.home.R;
import com.smart.home.activity.HomeActivity;

/**
 * Created by lenovo on 2017/4/16.
 */

public class SettingFragment extends BaseFragment {

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
    }


    @Override
    public boolean onBackPressed() {
        if(isAdded()){
            ((HomeActivity) getActivity()).showTab(0);
            return true;
        }
        return super.onBackPressed();
    }
}
