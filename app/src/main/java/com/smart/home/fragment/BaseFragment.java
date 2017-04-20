package com.smart.home.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;


import com.smart.home.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import rx.Subscription;


public class BaseFragment extends Fragment {

    private BaseActivity mActivity;
    private List<Subscription> mSubscriptions = new ArrayList<>();
    //视图状态处理类

    private Vector<Runnable> mDeferredRunnables = new Vector<>();
    protected View mView;


    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            if (!(getActivity() instanceof BaseActivity)) {
                throw new ClassCastException("Fragment所在的Activity必须为BaseActivity");
            }
             mActivity = (BaseActivity) getActivity();
        }

        return mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //执行延迟方法
        executeDeferred();
    }




    /**
     * 如果view不为null 先移除而后返回
     *
     * @return
     */
    protected boolean isViewNull() {
        boolean isNull = true;
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            isNull = false;
        }
        return isNull;
    }

    private void executeDeferred() {
        for (int i = mDeferredRunnables.size() - 1; i >= 0; i--) {
            Runnable runnable = mDeferredRunnables.get(i);
            runnable.run();
            mDeferredRunnables.remove(runnable);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBaseActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        clearSubscriptions();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onResumeFragment();
        } else {
            onPauseFramgent();
        }
    }


    /**
     * 唤醒fragment，不同于{@link #onResume()},它是fragment处于可见状态下调用的
     */
    public void onResumeFragment() {

    }

    /**
     * 暂停fragment, 不同于{@link #onPause()}, 它是fragment处于不可见状态下调用
     */
    public void onPauseFramgent() {

    }

    protected void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    protected void clearSubscriptions() {
        for (Subscription aSubscription : mSubscriptions) {
            aSubscription.unsubscribe();
        }

        mSubscriptions.clear();
    }






    public boolean onBackPressed() {
        return false;
    }

}
