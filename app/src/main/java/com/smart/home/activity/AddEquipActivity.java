package com.smart.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.smart.home.R;
import com.smart.home.model.EquipData;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.EquipDataPresenter;

import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by lenovo on 2017/4/21.
 */

public class AddEquipActivity extends BaseActivity implements QRCodeView.Delegate {

    private static final String TOOLBAR_TITLE = "添加设备";

    private String[] equipType = {"电灯", "电视", "空调", "风扇"};

    //判断是否在扫描界面
    private boolean isScanView = false;

    private Spinner mSpinner;

    private QRCodeView mQRCodeView;

    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.et_equip_position)
    EditText etEquipPosition;
    @BindView(R.id.btn_add_equip)
    Button btnAddEquip;

    public static void launch(Context context) {
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

        //初始化数据库
        EquipDataPresenter.getInstance().initDbHelp(this);
        mQRCodeView = (QRCodeView) findViewById(R.id.zxingview);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.view_spinner_item, equipType);
        mSpinner.setAdapter(adapter);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_code, R.id.btn_add_equip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_code:
                isScanView = true;
                mSpinner.setVisibility(View.GONE);
                llScan.setVisibility(View.GONE);
                etEquipPosition.setVisibility(View.GONE);
                btnAddEquip.setVisibility(View.GONE);
                mQRCodeView.setVisibility(View.VISIBLE);
                //委托代理完成扫描
                mQRCodeView.startCamera();
                mQRCodeView.setDelegate(this);
                break;
            //添加设备信息到数据库
            case R.id.btn_add_equip:
//                String equipPosition = etEquipPosition.getText().toString();
//                String equipCode = tvCode.getText().toString();
//                if(!TextUtils.isEmpty(equipCode) && !TextUtils.isEmpty(equipPosition))
                EquipDataPresenter.getInstance().insertData((String) mSpinner.getSelectedItem(), etEquipPosition.getText().toString(), tvCode.getText().toString());
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        mSpinner.setVisibility(View.GONE);
        llScan.setVisibility(View.VISIBLE);
        etEquipPosition.setVisibility(View.VISIBLE);
        btnAddEquip.setVisibility(View.VISIBLE);
        mQRCodeView.setVisibility(View.GONE);
        tvCode.setText(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isScanView) {
                mSpinner.setVisibility(View.GONE);
                llScan.setVisibility(View.VISIBLE);
                etEquipPosition.setVisibility(View.VISIBLE);
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
        if (mQRCodeView != null) {
            mQRCodeView = null;
        }
        super.onDestroy();
    }
}
