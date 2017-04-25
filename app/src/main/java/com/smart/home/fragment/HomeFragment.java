package com.smart.home.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.home.R;
import com.smart.home.activity.AddEquipActivity;
import com.smart.home.activity.AirConditionActivity;
import com.smart.home.activity.BulbControlActivity;
import com.smart.home.activity.FanActivity;
import com.smart.home.activity.HomeActivity;
import com.smart.home.activity.TvControlActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;

/**
 * Created by lenovo on 2017/4/16.
 */

public class HomeFragment extends BaseFragment {

    private Unbinder unbinder;



    private String[] dataSchema = {"局域网", "服务器", "红外"};

    private Spinner mSpinnerSchema;

    @BindView(R.id.iv_add_equip)
    ImageView ivAddEquip;
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
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mView);
    }

    private void initView(View view) {

        mSpinnerSchema = (Spinner) view.findViewById(R.id.spinner_schema);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.view_spinner_item, dataSchema);
        mSpinnerSchema.setAdapter(adapter);
    }



    @OnClick({R.id.iv_add_equip, R.id.iv_setting, R.id.iv_bulb, R.id.iv_air_condition, R.id.iv_tv, R.id.iv_fan})
    public void OnClick(View view){
        switch (view.getId()){
            //阅读说明
            case R.id.iv_add_equip:
                AddEquipActivity.launch(getActivity());
                break;
            //电灯
            case R.id.iv_bulb:
                BulbControlActivity.Launch(getActivity(), (String) mSpinnerSchema.getSelectedItem());
                break;
            //电视
            case R.id.iv_tv:
                TvControlActivity.launch(getActivity(), (String) mSpinnerSchema.getSelectedItem());
                break;
            //空调
            case R.id.iv_air_condition:
                AirConditionActivity.launch(getActivity(),(String) mSpinnerSchema.getSelectedItem());
                break;

            case R.id.iv_fan:
                FanActivity.Launch(getActivity(), (String) mSpinnerSchema.getSelectedItem());
                break;

            //设置
            case R.id.iv_setting:
                ((HomeActivity) getActivity()).showTab(1);
                break;

            default:
                break;
        }

    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();

    }
}
