package com.smart.home.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.smart.home.R;

import java.util.List;

/**
 * Created by 37X21=777 on 15/5/22.
 */
public class CustomDialogFactory {

    /**
     * @param mContext        上下文
     * @param title           对话框标题
     * @param message         对话框内容
     * @param btnText         按钮文本，默认：我知道了
     * @param clickListener   点击我知道了回调
     * @param dismissListener 对话框消失回调
     */
    public static AlertDialog showAlertDialog(Context mContext, boolean cancelable, CharSequence title, CharSequence message, CharSequence btnText, final OnDialogClickListener clickListener, final OnDialogDismissListener dismissListener) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("AlertDialog title and message can not be empty at same time");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.setCancelable(cancelable);
        alertDialog.getWindow().setContentView(R.layout.dialog_style_alert);
        TextView mTextViewTitle = (TextView) alertDialog.findViewById(R.id.title);
        TextView mTextViewMessage = (TextView) alertDialog.findViewById(R.id.message);
        TextView mButtonAlert = (TextView) alertDialog.findViewById(R.id.btn_alert);

        boolean isHasEmpty = TextUtils.isEmpty(title) || TextUtils.isEmpty(message);
        if (isHasEmpty) {
            CharSequence tmp = TextUtils.isEmpty(title) ? message : title;
            mTextViewTitle.setVisibility(View.GONE);
            mTextViewMessage.setText(tmp);
//            mTextViewMessage.setTextSize(mContext.getResources().getDimension(R.dimen.A));
        } else {
            mTextViewTitle.setText(title);
            mTextViewMessage.setText(message);
        }

//        if (cityName != null && message != null) {

//        }

        if (!TextUtils.isEmpty(btnText)) {
            mButtonAlert.setText(btnText);
        }

        mButtonAlert.setOnClickListener(l -> {
            alertDialog.dismiss();
            if (clickListener != null) {
                clickListener.onClick(alertDialog);
            }
        });

        alertDialog.setOnDismissListener(l -> {
            if (dismissListener != null) {
                dismissListener.onDismiss(alertDialog);
            }
        });

        return alertDialog;
    }

    /**
     * @param mContext        上下文
     * @param title           对话框标题
     * @param message         对话框内容
     * @param btnText         按钮文本，默认：我知道了
     * @param clickListener   点击我知道了回调
     * @param dismissListener 对话框消失回调
     */
    public static AlertDialog showAlertDialog(Context mContext, CharSequence title, CharSequence message, CharSequence btnText, final OnDialogClickListener clickListener, final OnDialogDismissListener dismissListener) {

        return showAlertDialog(mContext, false, title, message, btnText, clickListener, dismissListener);
    }

    /**
     * @param mContext 上下文
     * @param title    对话框标题
     * @param message  对话框内容
     * @param listener 点击我知道了回调
     */
    public static AlertDialog showAlertDialog(Context mContext, CharSequence title, CharSequence message, final OnDialogClickListener listener) {
        return showAlertDialog(mContext, title, message, "", listener, null);
    }

    /**
     * @param mContext 上下文
     * @param title    对话框标题
     * @param message  对话框内容
     */
    public static AlertDialog showAlertDialog(Context mContext, CharSequence title, CharSequence message) {
        return showAlertDialog(mContext, title, message, null);
    }

    /**
     * @param mContext 上下文
     * @param message  对话框内容
     */
    public static AlertDialog showAlertDialog(Context mContext, CharSequence message) {
        return showAlertDialog(mContext, "", message);
    }

    public static AlertDialog showLoginOutConfirmDialog(Context context, boolean cancelable,
                                                        CharSequence title, CharSequence content,
                                                        OnDialogClickListener positiveClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style_login, null);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView messageView = (TextView) view.findViewById(R.id.message);

        titleView.setText(title);
        messageView.setText(content);

        if (TextUtils.isEmpty(content)) {
            messageView.setVisibility(View.GONE);
        }

        return CustomDialogFactory.showConfirmDialog(
                context,
                false,
                view,
                context.getResources().getString(R.string.login_out),
                context.getResources().getString(R.string.cancel),
                positiveClickListener,
                null, null, true, true
        );
    }

    public static AlertDialog showLoginConfirmDialog(Context context, boolean cancelable,
                                                     CharSequence title, CharSequence content,
                                                     OnDialogClickListener positiveClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style_login, null);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView messageView = (TextView) view.findViewById(R.id.message);

        titleView.setText(title);
        messageView.setText(content);

        if (TextUtils.isEmpty(content)) {
            messageView.setVisibility(View.GONE);
        }

        return CustomDialogFactory.showConfirmDialog(
                context,
                false,
                view,
                context.getResources().getString(R.string.go_login),
                context.getResources().getString(R.string.cancel),
                positiveClickListener,
                null, null, true, true
        );
    }

    /**
     * 弹出确认框
     */
    public static AlertDialog showConfirmDialog(Context mContext, boolean cancelable, View contentView,
                                                CharSequence positiveButtonText, CharSequence negativeButtonText,
                                                OnDialogClickListener positiveClickListener,
                                                OnDialogClickListener negativeClickListener,
                                                OnDialogDismissListener dismissListener,
                                                boolean positiveAutoDismiss,
                                                boolean negativeAutoDismiss) {

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.setCancelable(cancelable);
        alertDialog.getWindow().setContentView(R.layout.dialog_style_confirm_view);
        //以下两句是为了解决，弹出框需要输入时，能够弹出输入法
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ViewGroup containerView = (ViewGroup) alertDialog.findViewById(R.id.fl_container);
        TextView mTextViewNegative = (TextView) alertDialog.findViewById(R.id.btn_confirm_left);
        TextView mTextViewPositive = (TextView) alertDialog.findViewById(R.id.btn_confirm_right);

        containerView.addView(contentView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        mTextViewPositive.setText(positiveButtonText);
        mTextViewNegative.setText(negativeButtonText);

        mTextViewPositive.setOnClickListener(l -> {
            if (positiveAutoDismiss)
                alertDialog.dismiss();
            if (positiveClickListener != null) {
                positiveClickListener.onClick(alertDialog);
            }
        });

        mTextViewNegative.setOnClickListener(l -> {
            if (negativeAutoDismiss)
                alertDialog.dismiss();
            if (negativeClickListener != null) {
                negativeClickListener.onClick(alertDialog);
            }
        });

        alertDialog.setOnDismissListener(l -> {
            if (dismissListener != null) {
                dismissListener.onDismiss(alertDialog);
            }
        });

        return alertDialog;
    }

    public static AlertDialog showConfirmDialog(Context mContext, boolean cancelable, CharSequence title,
                                                CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText,
                                                OnDialogClickListener positiveClickListener,
                                                OnDialogClickListener negativeClickListener,
                                                OnDialogDismissListener dismissListener) {
        return showConfirmDialog(mContext, cancelable, title, message, positiveButtonText, negativeButtonText,
                positiveClickListener, negativeClickListener, dismissListener, true, true);
    }

    /**
     * @param mContext              上下文
     * @param title                 对话框标题
     * @param message               对话框内容
     * @param positiveButtonText    对话框右边按钮文本
     * @param positiveClickListener 对话框右边按钮回调
     * @param negativeButtonText    对话框左边按钮文本
     * @param negativeClickListener 对话框左边按钮回调
     * @param dismissListener       对话框消失回调s
     */
    public static AlertDialog showConfirmDialog(Context mContext, boolean cancelable, CharSequence title,
                                                CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText,
                                                OnDialogClickListener positiveClickListener,
                                                OnDialogClickListener negativeClickListener,
                                                OnDialogDismissListener dismissListener,
                                                boolean positiveAutoDismiss,
                                                boolean negativeAutoDismiss) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("AlertDialog title and message can not be empty at same time");
        }

        if (TextUtils.isEmpty(positiveButtonText)) {
            throw new IllegalArgumentException("PositiveButtonText can not be empty...");
        }

        if (TextUtils.isEmpty(negativeButtonText)) {
            throw new IllegalArgumentException("NegativeButtonText can not be empty...");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.setCancelable(cancelable);
        alertDialog.getWindow().setContentView(R.layout.dialog_style_confirm);
        TextView mTextViewTitle = (TextView) alertDialog.findViewById(R.id.title);
        TextView mTextViewMessage = (TextView) alertDialog.findViewById(R.id.message);
        TextView mTextViewNegative = (TextView) alertDialog.findViewById(R.id.btn_confirm_left);
        TextView mTextViewPositive = (TextView) alertDialog.findViewById(R.id.btn_confirm_right);

        boolean isHasEmpty = TextUtils.isEmpty(title) || TextUtils.isEmpty(message);
        if (isHasEmpty) {
            CharSequence tmp = TextUtils.isEmpty(title) ? message : title;
            mTextViewTitle.setVisibility(View.GONE);
            mTextViewMessage.setText(tmp);
            mTextViewMessage.setGravity(Gravity.CENTER);
//            mTextViewMessage.setTextSize(mContext.getResources().getDimension(R.dimen.A));
        } else {
            mTextViewTitle.setText(title);
            mTextViewMessage.setText(message);
        }

        mTextViewPositive.setText(positiveButtonText);
        mTextViewNegative.setText(negativeButtonText);

        mTextViewPositive.setOnClickListener(l -> {
            if (positiveAutoDismiss)
                alertDialog.dismiss();
            if (positiveClickListener != null) {
                positiveClickListener.onClick(alertDialog);
            }
        });

        mTextViewNegative.setOnClickListener(l -> {
            if (negativeAutoDismiss)
                alertDialog.dismiss();
            if (negativeClickListener != null) {
                negativeClickListener.onClick(alertDialog);
            }
        });

        alertDialog.setOnDismissListener(l -> {
            if (dismissListener != null) {
                dismissListener.onDismiss(alertDialog);
            }
        });

        return alertDialog;
    }

    /**
     * @param mContext              上下文
     * @param title                 对话框标题
     * @param message               对话框内容
     * @param positiveButtonText    对话框右边按钮文本
     * @param positiveClickListener 对话框右边按钮回调
     * @param negativeButtonText    对话框左边按钮文本
     * @param negativeClickListener 对话框左边按钮回调
     * @param dismissListener       对话框消失回调s
     */
    public static AlertDialog showConfirmDialog(Context mContext, CharSequence title, CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText, OnDialogClickListener positiveClickListener, OnDialogClickListener negativeClickListener, OnDialogDismissListener dismissListener) {

        return showConfirmDialog(mContext, true, title, message, positiveButtonText, negativeButtonText, positiveClickListener, negativeClickListener, dismissListener);
    }

    /**
     * @param mContext              上下文
     * @param title                 对话框标题
     * @param message               对话框内容
     * @param positiveButtonText    对话框右边按钮文本
     * @param positiveClickListener 对话框右边按钮回调
     * @param negativeButtonText    对话框左边按钮文本
     * @param negativeClickListener 对话框左边按钮回调
     */
    public static AlertDialog showConfirmDialog(Context mContext, CharSequence title, CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText, OnDialogClickListener positiveClickListener, OnDialogClickListener negativeClickListener) {
        return showConfirmDialog(mContext, title, message, positiveButtonText, negativeButtonText, positiveClickListener, negativeClickListener, null);
    }

    public static AlertDialog showConfirmDialog(Context mContext, CharSequence title, OnDialogClickListener positiveClickListener) {
        return showConfirmDialog(mContext, title, "", "确定", "取消", positiveClickListener);
    }

    /**
     * @param mContext              上下文
     * @param title                 对话框标题
     * @param message               对话框内容
     * @param positiveButtonText    对话框右边按钮文本
     * @param positiveClickListener 对话框右边按钮回调
     * @param negativeButtonText    对话框左边按钮文本
     */
    public static AlertDialog showConfirmDialog(Context mContext, CharSequence title, CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText, OnDialogClickListener positiveClickListener) {
        return showConfirmDialog(mContext, title, message, positiveButtonText, negativeButtonText, positiveClickListener, null);
    }

    /**
     * @param mContext              上下文
     * @param message               对话框内容
     * @param positiveButtonText    对话框右边按钮文本
     * @param positiveClickListener 对话框右边按钮回调
     * @param negativeButtonText    对话框左边按钮文本
     */
    public static AlertDialog showConfirmDialog(Context mContext, CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText, OnDialogClickListener positiveClickListener) {
        return showConfirmDialog(mContext, "", message, positiveButtonText, negativeButtonText, positiveClickListener);
    }

    /**
     * @param mContext           上下文
     * @param title              对话框标题
     * @param message            对话框内容
     * @param positiveButtonText 对话框右边按钮文本
     * @param negativeButtonText 对话框左边按钮文本
     */
    public static AlertDialog showConfirmDialog(Context mContext, CharSequence title, CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText) {
        return showConfirmDialog(mContext, title, message, positiveButtonText, negativeButtonText, null);
    }


    /**
     * @param mContext           上下文
     * @param message            对话框标题
     * @param message            对话框内容
     * @param positiveButtonText 对话框右边按钮文本
     * @param negativeButtonText 对话框左边按钮文本
     */
    public static AlertDialog showConfirmDialog(Context mContext, CharSequence message, CharSequence positiveButtonText, CharSequence negativeButtonText) {
        return showConfirmDialog(mContext, "", message, positiveButtonText, negativeButtonText);
    }

    /**
     * 显示列表选择对话框
     *
     * @param mContext               上下文
     * @param canceledOnTouchOutside 触摸对话框以外的地方是否取消该对话框
     * @param title                  对话框标题
     * @param dataList               列表数据项
     * @param clickListener          点击列表某一项回调接口
     * @return
     */
    public static <T> AlertDialog showListDialog(Context mContext, boolean canceledOnTouchOutside,
                                                 CharSequence title, List<T> dataList, DialogInterface.OnClickListener clickListener) {
        if (TextUtils.isEmpty(title)) {
            throw new IllegalArgumentException("AlertDialog title can not be empty...");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        alertDialog.getWindow().setContentView(R.layout.dialog_style_list);
        TextView mTextViewTitle = (TextView) alertDialog.findViewById(R.id.title);
        ListView mListView = (ListView) alertDialog.findViewById(R.id.dialog_list);
        //设置标题
        mTextViewTitle.setText(title);
        //列表项
        ArrayAdapter<T> adapter = new ArrayAdapter<>(mContext, R.layout.dialog_style_list_item, R.id.list_item, dataList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (clickListener != null) {
                clickListener.onClick(alertDialog, position);
            }
            alertDialog.dismiss();
        });
        return alertDialog;
    }

    public static AlertDialog showFlowDialog(Context context, CharSequence title, CharSequence message) {
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("AlertDialog title and message can not be empty at same time");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setContentView(R.layout.dialog_style_flow);
        TextView mTextViewTitle = (TextView) alertDialog.findViewById(R.id.title);
        TextView mTextViewMessage = (TextView) alertDialog.findViewById(R.id.message);

        boolean isHasEmpty = TextUtils.isEmpty(title) || TextUtils.isEmpty(message);
        if (isHasEmpty) {
            CharSequence tmp = TextUtils.isEmpty(title) ? message : title;
            mTextViewTitle.setVisibility(View.GONE);
            mTextViewMessage.setText(tmp);
        } else {
            mTextViewTitle.setText(title);
            mTextViewMessage.setText(message);
        }

        return alertDialog;
    }

    public static AlertDialog showExplainDialog(Context context, boolean cancelable, OnDialogClickListener positiveClickListener, boolean positiveAutoDismiss){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(cancelable);
        alertDialog.setCancelable(cancelable);
        alertDialog.getWindow().setContentView(R.layout.dialog_style_explain);
        TextView mTextViewKnow = (TextView) alertDialog.findViewById(R.id.tv_know);

        mTextViewKnow.setOnClickListener(l -> {
            if (positiveAutoDismiss)
                alertDialog.dismiss();
            if (positiveClickListener != null) {
                positiveClickListener.onClick(alertDialog);
            }
        });

        return alertDialog;

    }


}
