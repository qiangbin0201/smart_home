package com.smart.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.smart.home.R;

import cn.carbs.android.library.AutoZoomInImageView;

/**
 * Created by lenovo on 2017/4/8.
 */

public class SplashScreenActivity extends BaseActivity {

    private AutoZoomInImageView mSplashImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initSplashView();



    }

    private void initSplashView() {
        mSplashImageView = (AutoZoomInImageView) findViewById(R.id.splash_iamgeview);
        mSplashImageView.post(new Runnable() {
            @Override
            public void run() {
                mSplashImageView.init().setScaleDelta(0.2f).setDurationMillis(5000)
                        .setOnZoomListener(new AutoZoomInImageView.OnZoomListener() {
                            @Override
                            public void onUpdate(View view, float progress) {

                            }

                            @Override
                            public void onEnd(View view) {
                                HomeActivity.launch(SplashScreenActivity.this);
                                finish();
                            }

                            @Override
                            public void onStart(View view) {

                            }
                        }).start(800);
            }
        });
    }
}
