package com.smart.home.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.smart.home.R;


/**
 * toolbar包装器，包装通用的toolbar使用样式，样式目前支持左边按钮+中间标题+右边按钮的样式
 */
public class ToolbarWrapper {
    private Context mContext;
    //系统toolbar
    protected Toolbar mToolbar;

    TextView mTextViewLeftButton;
    TextView mTextViewTitle;
    TextView mTextViewRightButton;
    View mDivider;


    public ToolbarWrapper(Context context) {
        mContext = context;
        mToolbar = (Toolbar) LayoutInflater.from(context).inflate(getToolbarLayoutId(), null);
        mTextViewRightButton = (TextView) mToolbar.findViewById(R.id.tv_right_button);
        mTextViewLeftButton = (TextView) mToolbar.findViewById(R.id.tv_left_button);
        mTextViewTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        mDivider = mToolbar.findViewById(R.id.divider_toolbar_content);
    }

    public ToolbarWrapper setBackgroundColorRes(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setBackgroundColor(mContext.getColor(colorRes));
        } else{
            mToolbar.setBackgroundColor(mContext.getResources().getColor(colorRes));
        }
        return this;
    }

    public ToolbarWrapper setTitleTextColorRes(int colorRes) {
        mTextViewTitle.setTextColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public ToolbarWrapper setBackTextColorRes(int colorRes) {
        mTextViewLeftButton.setTextColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public ToolbarWrapper setDividerColorRes(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mDivider.setBackgroundColor(mContext.getColor(colorRes));
        } else{
            mDivider.setBackgroundColor(mContext.getResources().getColor(colorRes));
        }
        return this;
    }

    protected int getToolbarLayoutId() {
        return R.layout.common_toolbar;
    }

    /**
     * 设置左边按钮文本
     *
     * @param text 按钮文本
     * @return
     */
    public ToolbarWrapper setLeftButtonText(CharSequence text) {
        mTextViewLeftButton.setText(text);
        return this;
    }

    /**
     * 设置左边按钮图标
     *
     * @param iconId 按钮图标资源ID
     * @return
     */
    public ToolbarWrapper setLeftButtonIcon(int iconId) {
        return setLeftButtonIcon(mContext.getResources().getDrawable(iconId));
    }

    /**
     * 设置左边按钮图标
     *
     * @param icon 按钮图标
     * @return
     */
    public ToolbarWrapper setLeftButtonIcon(Drawable icon) {
        mTextViewLeftButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        return this;
    }

    /**
     * 设置左边按钮点击回调
     *
     * @param listener 点击回调接口
     * @return
     */
    public ToolbarWrapper setLeftButtonClickListener(View.OnClickListener listener) {
        mTextViewLeftButton.setOnClickListener(listener);
        return this;
    }


    /**
     * 设置左边按钮的可见性
     *
     * @param visible
     * @return
     */
    public ToolbarWrapper setLeftButtonVisible(boolean visible) {
        mTextViewLeftButton.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 在右边按钮上添加小红点
     *
     * @return
     */
    public ToolbarWrapper showRedPointOnRightButton() {
        Drawable[] drawables = mTextViewRightButton.getCompoundDrawables();
        Drawable leftDrawable = (drawables != null && drawables.length > 0 ? drawables[0] : null);

        Drawable redPointDrawable = mContext.getResources().getDrawable(R.drawable.red_point);
        redPointDrawable.setBounds(-10, -20, redPointDrawable.getIntrinsicWidth() - 10, redPointDrawable.getIntrinsicHeight() - 20);
        mTextViewRightButton.setCompoundDrawables(leftDrawable, null, redPointDrawable, null);
        return this;
    }

    /**
     * 隐藏右边按钮上的小红点
     *
     * @return
     */
    public ToolbarWrapper hideRedPointOnRightButton() {
        Drawable[] drawables = mTextViewRightButton.getCompoundDrawables();
        Drawable leftDrawable = (drawables != null && drawables.length > 0 ? drawables[0] : null);
        mTextViewRightButton.setCompoundDrawables(leftDrawable, null, null, null);
        return this;
    }

    /**
     * 设置右边按钮文本
     *
     * @param text 按钮文本
     * @return
     */
    public ToolbarWrapper setRightButtonText(CharSequence text) {
        mTextViewRightButton.setText(text);
        return this;
    }

    /**
     * 设置右边按钮图标
     *
     * @param iconId 按钮图标资源ID
     * @return
     */
    public ToolbarWrapper setRightButtonIcon(int iconId) {
        if(iconId == 0) {
            return this;
        }
        return setRightButtonIcon(mContext.getResources().getDrawable(iconId));
    }

    /**
     * 设置右边按钮图标
     *
     * @param icon 按钮图标
     * @return
     */
    public ToolbarWrapper setRightButtonIcon(Drawable icon) {
        mTextViewRightButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        return this;
    }

    /**
     * 设置右边图标点击回调
     *
     * @param listener 点击回调接口
     * @return
     */
    public ToolbarWrapper setRightButtonClickListener(View.OnClickListener listener) {
        mTextViewRightButton.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置右边按钮的可见性
     *
     * @param visible
     * @return
     */
    public ToolbarWrapper setRightButtonVisible(boolean visible) {
        mTextViewRightButton.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置标题文本
     *
     * @param title 标题文本
     * @return
     */
    public ToolbarWrapper setTitle(CharSequence title) {
        mTextViewTitle.setText(title);
        return this;
    }

    /**
     * 设置右侧文案字体颜色
     */
    public void setRightButtonTextColor(int color) {
        mTextViewRightButton.setTextColor(color);
    }

    /**
     * 获取左边按钮文本
     *
     * @return
     */
    public CharSequence getLeftButtonText() {
        return mTextViewLeftButton.getText();
    }

    /**
     * 获取左边按钮图标
     *
     * @return
     */
    public Drawable getLeftButtonIcon() {
        return mTextViewLeftButton.getCompoundDrawables()[0];
    }

    /**
     * 获取左边按钮的可见性
     *
     * @return
     */
    public boolean isLeftButtonVisible() {
        return mTextViewLeftButton.getVisibility() == View.VISIBLE;
    }

    /**
     * 获取标题文本
     *
     * @return
     */
    public CharSequence getTitle() {
        return mTextViewTitle.getText();
    }

    /**
     * 获取右边按钮文本
     *
     * @return
     */
    public CharSequence getRightButtonText() {
        return mTextViewRightButton.getText();
    }

    /**
     * 获取右边按钮图标
     *
     * @return
     */
    public Drawable getRightButtonIcon() {
        return mTextViewRightButton.getCompoundDrawables()[0];
    }

    /**
     * 获取右边按钮的可见性
     *
     * @return
     */
    public boolean isRightButtonVisible() {
        return mTextViewRightButton.getVisibility() == View.VISIBLE;
    }

    /**
     * 获取系统的toolbar
     *
     * @return
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 获取左边按钮视图
     *
     * @return
     */
    public TextView getLeftButton() {
        return mTextViewLeftButton;
    }

    /**
     * 获取右边按钮视图
     *
     * @return
     */
    public TextView getRightButton() {
        return mTextViewRightButton;
    }

}
