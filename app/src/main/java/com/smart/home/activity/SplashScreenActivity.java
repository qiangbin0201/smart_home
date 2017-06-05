package com.smart.home.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.ImageView;

import com.smart.home.R;
import com.smart.home.presenter.StatusPresenter;

import java.util.Random;


/**
 * Created by lenovo on 2017/4/8.
 */

public class SplashScreenActivity extends BaseActivity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private ImageView mSplashImage;


    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Random r = new Random(SystemClock.elapsedRealtime());
        mSplashImage = (ImageView) findViewById(R.id.iv_splash);
        mSplashImage.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        animateImage();

        StatusPresenter mPasswordPresenter = StatusPresenter.getInstance(this, LockActivity.PASSWORD_FILE);

        password = mPasswordPresenter.getString(LockActivity.PASSWORD, "");


    }



    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(TextUtils.isEmpty(password)) {
                    HomeActivity.launch(SplashScreenActivity.this);
                    finish();
                }else {
                    LockActivity.Launch(SplashScreenActivity.this, LockActivity.TYPE_CHECK);
                    finish();
                }
            }
        });
    }

//    private void initSplashView() {
//        mSplashImageView = (AutoZoomInImageView) findViewById(R.id.splash_iamgeview);
//        mSplashImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                mSplashImageView.init().setScaleDelta(0.2f).setDurationMillis(5000)
//                        .setOnZoomListener(new AutoZoomInImageView.OnZoomListener() {
//                            @Override
//                            public void onUpdate(View view, float progress) {
//
//                            }
//
//                            @Override
//                            public void onEnd(View view) {
//                                HomeActivity.launch(SplashScreenActivity.this);
//                                finish();
//                            }
//
//                            @Override
//                            public void onStart(View view) {
//
//                            }
//                        }).start(800);
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
