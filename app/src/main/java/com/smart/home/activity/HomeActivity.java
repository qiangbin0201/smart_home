package com.smart.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.smart.home.R;
import com.smart.home.model.WeakRefHandler;

/**
 * Created by lenovo on 2017/4/4.
 */

public class HomeActivity extends BaseActivity {

    protected WeakRefHandler mWeakRefHandler;

    public static void launch(Activity context){
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        smoothSwitchScreen();
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setContentView(R.layout.activity_hone);

    }


    private void smoothSwitchScreen() {
        // 5.0以上修复了此bug
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup rootView = ((ViewGroup) this.findViewById(android.R.id.content));
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            rootView.setPadding(0, statusBarHeight, 0, 0);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            //在指定时间后需要将padding恢复
            mWeakRefHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rootView.setPadding(0, 0, 0, 0);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                }
            }, 3000);
        }
    }


}
