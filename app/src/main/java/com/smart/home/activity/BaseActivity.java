package com.smart.home.activity;

/**
 * Created by lenovo on 2017/4/4.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Activity的基类，原则上本应用系统中的所有Activity均应继承于本类，以便保证所有的应用结构及接口的统一
 * <p>
 * 增加以下内容：
 * <p>
 * 1、增加对toolbar的支持，使用方法如下：
 * <pre class="prettyprint">
 * protected void onCreate(Bundle savedInstanceState){
 * super.onCreate(savedInstanceState);
 * <p>
 * //setToolbar方法必须放在setContentView之前，否则会报错
 * setToolbar(ToolbarStyle.RETURN_TITLE, "标题");
 * <p>
 * setContentView(R.layout.content);
 * ...
 * }
 * </pre>
 * <p>
 * 3、增加对嵌套fragment进行{@code startActivityForResult}时无法在{@code onActivityResult}中接收到返回结果
 * 的系统bug处理
 * <p>
 * {@link #attachFragment}等等的方法支持
 * <p>
 * 5、实现了ViewGroupVO接口，增加对视图绑定的支持
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 最小的点击延时时间，单位：毫秒
     */
    private static final long MIN_CLICK_DELAY_TIME = 500;

    private List<Subscription> mSubscriptions = new ArrayList<>();
    //等待对话框
//    private ProgressDialogUtil mLoadingDialog;
    //进行startActivityForResult的fragment
    private Fragment mInteractiveFragment;

  

    //布局中的主容器
    private LinearLayout mLayoutContainer;
    //toolbar的包装器
    private ToolbarWrapper mToolbarWrapper;
    //是否已经设置了内容容器
    private boolean mIsSetContentView = false;

    //内容视图
    private View mContentView;

    //上一次点击的时间
    private long mLastClickTime;


    public static final String NAME = "当前页面";


    protected BaseActivity mBaseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = this;

        //初始化布局主容器
        initContainer();
        //初始化标题，处理intent传过来的标题
        initIntentTitle();
        //去掉actionbar下的阴影
        initActionBarShadow();
    }


    private boolean mIsPageLoaded = true;

    /**
     * 判断界面是否加载完成，默认都是加载完成，只有webview和部分需要在加载完数据后才展示标题的activity重载返回false
     *
     * @return
     */
    public boolean isPageLoaded() {
        return mIsPageLoaded;
    }

    public void setPageLoaded(boolean isPageLoaded) {
        mIsPageLoaded = isPageLoaded;
    }



    /**
     * 获取对应的页面名称
     */
    public String getPageName() {
        if (!TextUtils.isEmpty(mPageName)) {
            return mPageName;
        }
        return getToolbarWrapper() != null ? getToolbarWrapper().getTitle() + "" : "";
    }

    private String mPageName;

    /**
     * 设置界面名称
     *
     * @param pageName
     */
    public void setPageName(String pageName) {
        mPageName = pageName;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        return v;
    }

//    public SwipeBackLayout getSwipeBackLayout() {
//        return this.mSwipeBackHandler.getSwipeBackLayout();
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //hesc 2016.4.13 这种处理太霸道，把所有的touch事件都拦截了，导致很多触摸事件延迟，暂时先注掉
        //为了防止快速点击按钮，导致点击事件多次触发，这里判断两次点击的时间是否在合理范围，否则直接拦截touch事件分发
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            long time = System.currentTimeMillis();
//            long delta = time - mLastClickTime;
//            if (delta < MIN_CLICK_DELAY_TIME) {
//                return true;
//            } else {
//                mLastClickTime = time;
//            }
//        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 去掉actionbar下的阴影
     */
    private void initActionBarShadow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    public ToolbarWrapper getToolbarWrapper() {
        return mToolbarWrapper;
    }


    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        //隐藏系统的actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setHasActionBarAsFalse();
        if (toolbar != null) {
            super.setSupportActionBar(toolbar);
        }
    }

    /**
     * 查找android.support.v7.app.AppCompatDelegateImplBase类
     *
     * @param c
     * @return
     */
    private Class getAppCompatDelegateImplBaseClass(Class c) {
        if (c == null)
            return null;
        else if (c.getName().equals("android.support.v7.app.AppCompatDelegateImplBase"))
            return c;
        else
            return getAppCompatDelegateImplBaseClass(c.getSuperclass());
    }

    protected void setHasActionBarAsFalse() {
        Class c = getAppCompatDelegateImplBaseClass(getDelegate().getClass());
        if (c == null) return;
        try {
            Field hasActionBarField = c.getDeclaredField("mHasActionBar");
            hasActionBarField.setAccessible(true);
            hasActionBarField.setBoolean(getDelegate(), false);

            Field windowNoTitleField = c.getDeclaredField("mWindowNoTitle");
            windowNoTitleField.setAccessible(true);
            windowNoTitleField.setBoolean(getDelegate(), true);

            Field actionBarField = c.getDeclaredField("mActionBar");
            actionBarField.setAccessible(true);
            actionBarField.set(getDelegate(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View contentView = getLayoutInflater().inflate(layoutResID, null);
        setContentViewToContainer(contentView, createMatchParentLayoutParams());
        mIsSetContentView = true;
    }

    @Override
    public void setContentView(View contentView) {
        setContentViewToContainer(contentView, createMatchParentLayoutParams());
        mIsSetContentView = true;
    }

    @Override
    public void setContentView(View contentView, ViewGroup.LayoutParams params) {
        setContentViewToContainer(contentView, params);
        mIsSetContentView = true;
    }

    @Override
    public void addContentView(View contentView, ViewGroup.LayoutParams params) {
        mLayoutContainer.addView(contentView, params);
        super.setContentView(mLayoutContainer, createMatchParentLayoutParams());
    }

    /**
     * 获取内容视图
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    private void setContentViewToContainer(View contentView, ViewGroup.LayoutParams params) {
        mContentView = contentView;

        FrameLayout decorView = new FrameLayout(this);
        decorView.addView(contentView, params);
        mLayoutContainer.addView(decorView, createMatchParentLayoutParams());
        super.setContentView(mLayoutContainer, createMatchParentLayoutParams());

    }

    private ViewGroup.LayoutParams createMatchParentLayoutParams() {
        return new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 初始化布局主容器
     */
    private void initContainer() {
        if (mLayoutContainer == null) {
            mLayoutContainer = new LinearLayout(this);
            mLayoutContainer.setOrientation(LinearLayout.VERTICAL);
        }
    }

    private void initIntentTitle() {
        if (TextUtils.isEmpty(getIntent().getStringExtra("title"))) return;

        try {
            setTitle(getIntent().getStringExtra("title"));
        } catch (Exception e) {
            //Log.e("setTitleFailed",e.getMessage());
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mToolbarWrapper != null)
            mToolbarWrapper.setTitle(title);
        super.setTitle(title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        clearSubscriptions();
//        dismissLoadingDialog();
//        ShareManager.getInstance().onDestroy();
        mBaseActivity = null;
        super.onDestroy();
    }

    /**
     * 把Observable观察者添加到列表中，以便在Activity销毁时能够取消未执行的Observable
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    /**
     * 取消所有的Observable的订阅
     */
    protected void clearSubscriptions() {
        for (Subscription aSubscription : mSubscriptions) {
            aSubscription.unsubscribe();
        }
        mSubscriptions.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            killSelf();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            killSelf();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 关闭本Activity，如果本Activity处于任务栈最底部，则跳转到NavigationActivity中
     */
    public void killSelf() {
        if (isTaskRoot() && !isHomeActivity()) {
            launchHomeActivity();
        }
        finish();
    }

    /**
     * 判断是否主页面
     *
     * @return
     */
    private boolean isHomeActivity() {
        return getClass().getName().equals(HomeActivity.class.getName());
    }

    /**
     * 跳转到主页面
     */
    private void launchHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    /**
     * 获取 点击焦点外可消失的等待框
     */
    public void showLoadingDialog(String message, boolean cancelable, boolean cancelableOutside) {
        if (this.isFinishing()) return;

//        if (mLoadingDialog == null) {
//            mLoadingDialog = new ProgressDialogUtil(this);
//        }
//
//        mLoadingDialog.show(message, cancelable, cancelableOutside, new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                onLoadingDialogCanceled();
//            }
//        });
    }

    /**
     * 显示等待框
     *
     * @param message
     * @param cancelable
     */
    public void showLoadingDialog(String message, boolean cancelable) {
        showLoadingDialog(message, cancelable, false);
//        dismissLoadingDialog();
//        if (mLoadingDialog == null) {
//            mLoadingDialog = new ProgressDialogUtil(this);
//        }
//
//        mLoadingDialog.onShow(message, cancelable, d -> onLoadingDialogCanceled());
    }

    /**
     * 隐藏等待框
     */
//    public void dismissLoadingDialog() {
//        if (mLoadingDialog != null) {
//          //  mLoadingDialog.hide();
//            mLoadingDialog = null;
//        }
//    }

    /**
     * 当等待框取消时的处理方法
     */
//    protected void onLoadingDialogCanceled() {
//        mLoadingDialog = null;
//    }


    /**
     * 以toast形式显示消息
     *
     * @param message
     */


    /**
     * 构建fragment，当缓存中不存在fragment时，调用factory创建fragment
     *
     * @param containerViewId fragment所在的容器的id.
     * @param tag             fragment的唯一标识
     * @param factory         创建fragment的工厂接口
     */
    protected Fragment attachFragment(int containerViewId, String tag, FragmentFactory factory) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (factory == null) {
                throw new IllegalArgumentException("参数factory不能为空");
            } else {
                fragment = factory.createFragment();
            }
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment).commitAllowingStateLoss();
        return fragment;
    }

    /**
     * 隐藏软键盘
     */

    /**
     * 显示软键盘
     */



    /**
     * 当前activity是否处于前台
     *
     * @return
     */


}

