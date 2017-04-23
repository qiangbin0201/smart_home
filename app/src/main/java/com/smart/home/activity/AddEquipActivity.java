package com.smart.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.smart.home.R;
import com.smart.home.model.ToolbarStyle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by lenovo on 2017/4/21.
 */

public class AddEquipActivity extends BaseActivity implements QRCodeView.Delegate{

    private static final String TOOLBAR_TITLE = "添加设备";

    //判断是否在扫描界面
    private  boolean isScanView = false;

    private QRCodeView mQRCodeView;

    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.et_equip_name)
    EditText etEquipName;
    @BindView(R.id.btn_add_equip)
    Button btnAddEquip;

    public static void launch(Context context){
        Intent intent = new Intent(context, AddEquipActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);

        setContentView(R.layout.activity_add_equip);
        mQRCodeView = (QRCodeView) findViewById(R.id.zxingview);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_code, R.id.btn_add_equip})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_code:
                isScanView = true;
                llScan.setVisibility(View.GONE);
                etEquipName.setVisibility(View.GONE);
                btnAddEquip.setVisibility(View.GONE);
                mQRCodeView.setVisibility(View.VISIBLE);
                //委托代理完成扫描
                mQRCodeView.startCamera();
                mQRCodeView.setDelegate(this);
                break;
            case R.id.btn_add_equip:
                break;
            default:
                break;
        }

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        llScan.setVisibility(View.VISIBLE);
        etEquipName.setVisibility(View.VISIBLE);
        btnAddEquip.setVisibility(View.VISIBLE);
        mQRCodeView.setVisibility(View.GONE);
        tvCode.setText(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isScanView) {
                llScan.setVisibility(View.VISIBLE);
                etEquipName.setVisibility(View.VISIBLE);
                btnAddEquip.setVisibility(View.VISIBLE);
                mQRCodeView.setVisibility(View.GONE);
                isScanView = false;
                return true;
            }
            return super.onKeyDown(keyCode, event);

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if(mQRCodeView != null){
            mQRCodeView = null;
        }
        super.onDestroy();
    }
}
