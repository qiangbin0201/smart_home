package com.smart.home.model;

import android.view.View;

import com.smart.home.R;
import com.smart.home.activity.BaseActivity;

/**
 * Created by lenovo on 2017/4/20.
 */

public class ToolbarFactory {
    /**
     * 创建toolbar
     *
     * @param activity
     * @param style
     * @param title
     * @param rightRes
     * @param rightClickListener
     * @return
     */
    public static ToolbarWrapper createToolbar(BaseActivity activity, ToolbarStyle style,
                                               CharSequence title, Object rightRes,
                                               View.OnClickListener rightClickListener) {

        if (style == ToolbarStyle.RETURN_TITLE) {
            //样式为返回按钮+标题
            return new ToolbarWrapper(activity)
                    .setLeftButtonVisible(true)
//                    .setLeftButtonText("")
                    .setLeftButtonIcon(R.drawable.img_title_back)
                    .setDividerColorRes(R.color.bg_app)
                    .setLeftButtonClickListener(v -> callReturnClick(activity))
                    .setRightButtonVisible(false)
                    .setTitle(title);
        } else if(style == ToolbarStyle.RETURN_TITLE_ICON){
            //样式为返回按钮+标题+右边图标
            return new ToolbarWrapper(activity)
                    .setLeftButtonVisible(true)
//                    .setLeftButtonText("")
                    .setLeftButtonIcon(R.drawable.img_title_back)
                    .setLeftButtonClickListener(v->callReturnClick(activity))
                    .setRightButtonVisible(true)
                    .setRightButtonText("")
                    .setRightButtonIcon((Integer)rightRes)
                    .setRightButtonClickListener(rightClickListener)
                    .setTitle(title);
        } else {
            throw new IllegalArgumentException("style参数非法，不支持该toolbar样式");
        }
    }



    private static void callReturnClick(BaseActivity activity){
        //隐藏键盘
        activity.hideKeyboard();
        //关闭
        activity.killSelf();
    }
}
