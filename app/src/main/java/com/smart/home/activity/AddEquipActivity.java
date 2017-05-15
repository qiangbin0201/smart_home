package com.smart.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.zxing.Result;
//import com.google.zxing.client.android.AutoScannerView;
//import com.google.zxing.client.android.BaseCaptureActivity;
import com.smart.home.R;
import com.smart.home.model.EquipData;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.EquipDataPresenter;

import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

/**
 * Created by lenovo on 2017/4/21.
 */

public class AddEquipActivity extends BaseActivity implements QRCodeView.Delegate {

    private static final String TOOLBAR_TITLE = "添加设备";

    private String[] equipType = {"电灯", "电视", "空调", "风扇"};

    //判断是否在扫描界面
    private boolean isScanView = false;

    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private Spinner mSpinner;

    private SurfaceView surfaceView;
//    private AutoScannerView autoScannerView;

//    private QRCodeView mQRCodeView;

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
        overridePendingTransition(R.anim.images_detail_screen_in, R.anim.images_detail_screen_out);
//        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);

        setContentView(R.layout.activity_add_equip);

        //初始化数据库
        EquipDataPresenter.getInstance().initDbHelp(this);
//        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
//        autoScannerView = (AutoScannerView) findViewById(R.id.autoscanner_view);
        //mQRCodeView = (QRCodeView) findViewById(R.id.zxingview);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.view_spinner_item, equipType);
        mSpinner.setAdapter(adapter);

        ButterKnife.bind(this);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mQRCodeView.startCamera();
//        mQRCodeView.startSpot();
//    }

    @Override
    protected void onResume() {
   //     mQRCodeView.showScanRect();

        super.onResume();

    }

    @Override
    protected void onStop() {
  //      mQRCodeView.stopCamera();
        super.onStop();
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
//                autoScannerView.setCameraManager(cameraManager);
//                mQRCodeView.setVisibility(View.VISIBLE);
//                //委托代理完成扫描
//                mQRCodeView.startCamera();
//                mQRCodeView.startSpot();
//                mQRCodeView.showScanRect();
//                mQRCodeView.setDelegate(this);
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
        vibrate();
        mSpinner.setVisibility(View.GONE);
        llScan.setVisibility(View.VISIBLE);
        etEquipPosition.setVisibility(View.VISIBLE);
        btnAddEquip.setVisibility(View.VISIBLE);
        tvCode.setText(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {


    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isScanView) {
                mSpinner.setVisibility(View.GONE);
                llScan.setVisibility(View.VISIBLE);
                etEquipPosition.setVisibility(View.VISIBLE);
                btnAddEquip.setVisibility(View.VISIBLE);
//                mQRCodeView.setVisibility(View.GONE);
                isScanView = false;
                return true;
            }
            return super.onKeyDown(keyCode, event);

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
//        if (mQRCodeView != null) {
//            mQRCodeView = null;
//        }
        super.onDestroy();
    }

//    @Override
//    public SurfaceView getSurfaceView() {
//        return  (surfaceView == null) ? (SurfaceView) findViewById(R.id.preview_view) : surfaceView;
//    }
//
//    @Override
//    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
//        playBeepSoundAndVibrate(true, false);
//        vibrate();
//        mSpinner.setVisibility(View.GONE);
//        llScan.setVisibility(View.VISIBLE);
//        etEquipPosition.setVisibility(View.VISIBLE);
//        btnAddEquip.setVisibility(View.VISIBLE);
//        tvCode.setText(rawResult.getText());
//        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_LONG).show();
//
//    }
}
