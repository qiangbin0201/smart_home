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
import android.widget.Toast;

import com.smart.home.R;
import com.smart.home.activity.BulbControlActivity;
import com.smart.home.activity.HomeActivity;

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

    String[] arr = { "孙悟空", "猪八戒", "唐僧" };

    private RadioGroup mRadioGroup;

    private Spinner mSpinner;

    private RadioButton rbWifi, rbServer, rbInfrared;

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
//    @BindView(R.id.rb_wifi)
//    RadioButton rbFan;
//    @BindView(R.id.rb_server)
//    RadioButton rbServer;
//    @BindView(R.id.rb_infrared)
//    RadioButton rbInfrared;




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
        unbinder = ButterKnife.bind(this,mView);
        initView(mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initView(View view) {
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        rbWifi = (RadioButton) view.findViewById(R.id.rb_wifi);
        rbWifi.setOnClickListener(mOnClickListener);
        rbServer = (RadioButton) view.findViewById(R.id.rb_server);
        rbServer.setOnClickListener(mOnClickListener);
        rbInfrared = (RadioButton) view.findViewById(R.id.rb_infrared);
        rbInfrared.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rb_wifi:
                    Toast.makeText(getActivity(), "one", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rb_server:
                    Toast.makeText(getActivity(), "two", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rb_infrared:
                    Toast.makeText(getActivity(), "three", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @OnClick({R.id.iv_explain, R.id.iv_setting, R.id.iv_bulb, R.id.iv_air_condition, R.id.iv_tv, R.id.iv_fan})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rb_infrared:
                Toast.makeText(getActivity(), "局域网", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_server:
                Toast.makeText(getActivity(), "红外", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_wifi:
                Toast.makeText(getActivity(), "服务器", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_explain:
                break;
            case R.id.iv_bulb:
                BulbControlActivity.Launch(getActivity());
                break;
            case R.id.iv_setting:
                ((HomeActivity) getActivity()).showTab(1);
                break;
        }

    }
//    @OnCheckedChanged({R.id.rb_infrared, R.id.rb_server, R.id.rb_wifi})
//    public void OnCheckedChanged(RadioButton buttonView, boolean isChecked){
//        switch (buttonView.getId()){
//            case R.id.rb_infrared:
//                Toast.makeText(getActivity(), "局域网", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.rb_server:
//                Toast.makeText(getActivity(), "红外", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.rb_wifi:
//                Toast.makeText(getActivity(), "服务器", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }


    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_wifi:
                    Toast.makeText(getActivity(), "one", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rb_infrared:
                    Toast.makeText(getActivity(), "two", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rb_server:
                    Toast.makeText(getActivity(), "three", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();

    }
}
