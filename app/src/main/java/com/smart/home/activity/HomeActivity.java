package com.smart.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.smart.home.R;
import com.smart.home.fragment.HomeFragment;
import com.smart.home.fragment.SettingFragment;
import com.smart.home.model.WeakRefHandler;

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


}
