package com.smart.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.smart.home.R;
import com.smart.home.fragment.BaseFragment;
import com.smart.home.fragment.HomeFragment;
import com.smart.home.fragment.SettingFragment;
import com.smart.home.model.WeakRefHandler;
import com.smart.home.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/4.
 */

public class HomeActivity extends BaseActivity {

    protected WeakRefHandler mWeakRefHandler;

    private int mCurrentFragmentIndex = 0;

    private List<Fragment> fragments = new ArrayList<Fragment>();

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
        initFragment();

    }


    private void initFragment(){
        fragments.add(HomeFragment.newInstance());
        fragments.add(SettingFragment.newInstance());
        showTab(0);

    }


    /**
     * 处理菜单选择
     *
     * @param index
     */
    public boolean showTab(int index) {
        Fragment fragment = getFragment(index);
        //拦截选择事件

        mCurrentFragmentIndex = index;
        if (fragment != null) {
            addFragment(fragment);
            showFragment(fragment);
            return true;
        }

        return false;
    }


    private void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = getFragmentTransaction();
            ft.add(R.id.ContentFrame, fragment);
            ft.commitAllowingStateLoss();
        }
    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment f = fragments.get(i);
            if (f == fragment) {
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_still);
                ft.show(f);
            } else {
                ft.hide(f);
            }
        }

        ft.commitAllowingStateLoss();
    }

    /**
            * 获取当前正在显示的Fragment
    *
            * @param index
    * @return
            */
    public Fragment getFragment(int index) {
        if (index < 0 || index > fragments.size() - 1) {
            return null;
        }
        return fragments.get(index);
    }


    private FragmentTransaction getFragmentTransaction() {
        return this.getSupportFragmentManager().beginTransaction();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }

            Fragment fragment = fragments.get(mCurrentFragmentIndex);
            if (fragment instanceof BaseFragment) {
                boolean tag = ((BaseFragment) fragment).onBackPressed();
                if (tag) {
                    return tag;
                }
            }

            //退出程序
            exit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private boolean mPendingExit = false;

    private void exit() {
        if (mPendingExit) {
            finish();
        } else {
            mPendingExit = true;
            ToastUtil.showBottom(this, "再次点击退出应用");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPendingExit = false;
                }
            }, 2000);
        }
    }


}
