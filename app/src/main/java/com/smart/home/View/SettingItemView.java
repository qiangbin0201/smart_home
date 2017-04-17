package com.smart.home.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.smart.home.R;
import com.smart.home.utils.ViewUtils;

/**
 * Created by lenovo on 2017/4/17.
 */

public class SettingItemView extends FrameLayout {

    private Context mContext;
    // item的标题
    private String title;
    // 是否显示后面的箭头
    private boolean hasMore = true;
    // item的左右两边的imageview
    private ImageView img_left, img_right;
    // 标题textview
    private TextView tv_title;
    // 标题颜色
    private ColorStateList titleColor;
    // 左边的图标id
    private int leftIconId;
    // 文字，右侧
    private TextView rightText;
    //
    private SimpleDraweeView userImg;
    //
    private boolean showRightText, showUserImg;// , editAble;
    private String editTitle;
    private View mVNotify;


    public SettingItemView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = getResources();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MineItem);
        titleColor = res.getColorStateList(R.color.tab_text_color);
        try {
            if (a.hasValue(R.styleable.MineItem_titleD)) {
                title = a.getString(R.styleable.MineItem_titleD);
            }
            if (a.hasValue(R.styleable.MineItem_editD)) {
                editTitle = a.getString(R.styleable.MineItem_editD);
            }
            if (a.hasValue(R.styleable.MineItem_hasMore)) {
                hasMore = a.getBoolean(R.styleable.MineItem_hasMore, true);
            }
            if (a.hasValue(R.styleable.MineItem_leftIcon)) {
                leftIconId = a.getResourceId(R.styleable.MineItem_leftIcon, 0);
            }
            if (a.hasValue(R.styleable.MineItem_titleColor)) {
                titleColor = a
                        .getColorStateList(R.styleable.MineItem_titleColor);
            }
            showRightText = a.getBoolean(R.styleable.MineItem_showRightText,
                    false);
            showUserImg = a.getBoolean(R.styleable.MineItem_showRightImg,
                    false);
            // editAble = a.getBoolean(R.styleable.MineItem_editable, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        a.recycle();
        initView();

    }

    private void initView() {
        try {
            LayoutInflater.from(getContext()).inflate(R.layout.view_mine_item,
                    this);

            tv_title = (TextView) findViewById(R.id.view_set_tvtitle);
            tv_title.setTextColor(titleColor);

            img_left = (ImageView) findViewById(R.id.view_set_imgleft);
            img_right = (ImageView) findViewById(R.id.view_set_imgright);

            rightText = (TextView) findViewById(R.id.view_set_edit);
            userImg = (SimpleDraweeView) findViewById(
                    R.id.view_setting_userImg);

            mVNotify = findViewById(R.id.iv_notify);

            // editText.setEnabled(editAble);
            // editText.setFocusable(editAble);
            // editText.setFocusableInTouchMode(editAble);
            rightText.setVisibility(showRightText ? VISIBLE : GONE);
            userImg.setVisibility(showUserImg ? VISIBLE : GONE);

            if (title != null && !title.isEmpty()) {
                setTitle(title);
            }

            if (editTitle != null && !editTitle.isEmpty()) {
                setRightText(editTitle);
            }

            if (leftIconId > 0) {
                img_left.setImageResource(leftIconId);
                img_left.setVisibility(View.VISIBLE);
            } else {
                img_left.setVisibility(View.GONE);
            }

            if (hasMore) {
                img_right.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setUserImg(String url) {
        if (TextUtils.isEmpty(url) || userImg.getVisibility() != VISIBLE) {
            return;
        }
        userImg.setImageURI(Uri.parse(url));

    }

    public void setRightText(String txt) {
        this.editTitle = txt;
        if (rightText.getVisibility()!=View.VISIBLE){
            rightText.setVisibility(View.VISIBLE);
        }
        rightText.setText(editTitle);
    }

    public CharSequence getRightTitle() {
        return rightText.getText();
    }

    public TextView getRightText() {
        return rightText;
    }

    public void setTitle(String title) {
        this.title = title;
        tv_title.setText(title);
    }

    /**
     * 显示或隐藏提醒标识点
     *
     * @param visible
     */
    public void showNotify(boolean visible) {
        ViewUtils.showView(mVNotify, visible);
    }
}
