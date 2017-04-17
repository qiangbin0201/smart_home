package com.smart.home.View;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.smart.home.R;

/**
 * <p>
 * 视图状态处理类，主要处理在不同视图状态下显示不同视图的场景
 * </p>
 */
public class ViewStateHandler {
    //布局容器视图
    protected ViewGroup mContainerView;

    //上下文
    protected Context mContext;
    //布局文件解析器
    protected LayoutInflater mLayoutInflater;
    //当前的视图
    private View mCurrentView;

    //加载错误视图
    private View mErrorView;
    //记录为空视图
    private View mEmptyView;
    //加载中视图
    private View mLoadingView;
    //加载完成后显示视图
    private View mLoadedView;

    public ViewStateHandler(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        //设置默认的视图状态
    }

    /**
     * 获取布局主容器
     *
     * @return
     */
    public ViewGroup getContainerView() {
        return mContainerView;
    }

    /**
     * 设置布局主容器
     *
     * @param containerView
     */
    public void setContainerView(ViewGroup containerView) {
        if (mContainerView == containerView) return;
        //清除原来布局里的子视图
        clearChildView(mContainerView);
        mContainerView = containerView;
        setLoadedView();
        //默认不显示加载完成视图
        if (mLoadedView != null)
            mLoadedView.setVisibility(View.GONE);
        mCurrentView = null;
    }

    /**
     * 设置加载完成视图
     */
    protected void setLoadedView() {
        //默认取布局主视图的第一个子视图作为加载完成视图
        if (mContainerView != null && mContainerView.getChildCount() > 0) {
            mLoadedView = mContainerView.getChildAt(0);
        } else {
            mLoadedView = null;
        }
    }

    /**
     * 清除布局里的子视图
     *
     * @param containerView
     */
    private void clearChildView(ViewGroup containerView) {
        if (containerView == null) return;
        if (mEmptyView != null)
            containerView.removeView(mEmptyView);
        if (mLoadingView != null)
            containerView.removeView(mLoadingView);
        if (mErrorView != null)
            containerView.removeView(mErrorView);
        //加载完成视图只能设置visibility属性，如果使用removeView，则会报错
        if (mLoadedView != null)
            mLoadedView.setVisibility(View.VISIBLE);
    }









    /**
     * 获取当前显示的视图
     *
     * @return
     */
    public View getCurrentView() {
        return mCurrentView;
    }

    /**
     * 获取加载错误视图
     *
     * @return
     */
    public View getErrorView() {
        return mErrorView;
    }

    /**
     * 设置加载错误视图
     *
     * @param errorView
     */
    public void setErrorView(View errorView) {
        if (mErrorView == errorView) return;
        if (mErrorView != null && mContainerView != null)
            mContainerView.removeView(mErrorView);
        if (mCurrentView == mErrorView) {
            mCurrentView = null;
        }

        mErrorView = errorView;
    }




    /**
     * 获取记录为空视图
     *
     * @return
     */
    public View getEmptyView() {
        return mEmptyView;
    }





    /**
     * 设置记录为空视图
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        if (mEmptyView == emptyView) return;
        if (mEmptyView != null && mContainerView != null)
            mContainerView.removeView(mEmptyView);
        if (mCurrentView == mEmptyView) {
            mCurrentView = null;
        }

        mEmptyView = emptyView;
    }

    /**
     * 获取正在加载的视图
     *
     * @return
     */
    public View getLoadingView() {
        return mLoadingView;
    }

    /**
     * 设置正在加载视图
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        if (mLoadingView == loadingView) return;
        if (mLoadingView != null && mContainerView != null)
            mContainerView.removeView(mLoadingView);
        if (mCurrentView == mLoadingView) {
            mCurrentView = null;
        }

        mLoadingView = loadingView;
    }

    /**
     * 获取加载完成视图
     *
     * @return
     */
    public View getLoadedView() {
        return mLoadedView;
    }




//    public void setLoadingViewAnimationResource(int resId) {
//        if (mLoadingView instanceof DefaultLoadingView) {
//            ((DefaultLoadingView) mLoadingView).setLoadingViewAnimationResource(resId);
//        }
//    }

}
