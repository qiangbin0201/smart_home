package com.smart.home.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.home.R;

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
    private ImageView img_left;
    // 标题textview
    private TextView tv_title;
    // 左边的图标id
    private int leftIconId;



    public SettingItemView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources res = getResources();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MineItem);
        try {
            if (a.hasValue(R.styleable.MineItem_titleD)) {
                title = a.getString(R.styleable.MineItem_titleD);
            }
            if (a.hasValue(R.styleable.MineItem_leftIcon)) {
                leftIconId = a.getResourceId(R.styleable.MineItem_leftIcon, 0);
            }
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

            tv_title = (TextView) findViewById(R.id.tv_title);


            img_left = (ImageView) findViewById(R.id.iv_title);


            if (title != null && !title.isEmpty()) {
                setTitle(title);
            }


            if (leftIconId > 0) {
                img_left.setImageResource(leftIconId);
                img_left.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTitle(String title) {
        this.title = title;
        tv_title.setText(title);
    }
}
