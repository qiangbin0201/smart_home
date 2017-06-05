package com.smart.home.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.home.R;
import com.smart.home.View.SettingItemView;
import com.smart.home.activity.FeedbackActivity;
import com.smart.home.activity.HomeActivity;
import com.smart.home.activity.LockActivity;
import com.smart.home.activity.MyEquipActivity;
import com.smart.home.activity.SettingLockActivity;
import com.smart.home.presenter.EquipDataPresenter;
import com.smart.home.utils.CustomDialogFactory;
import com.smart.home.utils.OnDialogClickListener;
import com.smart.home.utils.ToastUtil;

/**
 * Created by qiangbin on 2017/4/16.
 */

public class SettingFragment extends BaseFragment {

    private static final String DIALOG_TITLE = "删除设备";

    private static final String DIALOG_CONTENT = "您确定要删除全部设备吗";

    private TextView tv_back;

    private EquipDataPresenter mEquipDataPresenter;

    private SettingItemView mViewClear, mViewVersion, mViewFeedback, mViewEquip, mViewExplain, mViewLock;

    public static SettingFragment newInstance(){
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mView);
    }

    private void initView(View view) {
        tv_back = (TextView) view.findViewById(R.id.tv_back);
        tv_back.setOnClickListener(mOnClickListener);
        mViewClear = (SettingItemView) view.findViewById(R.id.view_clear);
        mViewClear.setOnClickListener(mOnClickListener);
        mViewFeedback = (SettingItemView) view.findViewById(R.id.view_feedback);
        mViewFeedback.setOnClickListener(mOnClickListener);
        mViewVersion = (SettingItemView) view.findViewById(R.id.view_version);
        mViewVersion.setOnClickListener(mOnClickListener);
        mViewEquip = (SettingItemView) view.findViewById(R.id.view_equip);
        mViewEquip.setOnClickListener(mOnClickListener);
        mViewExplain = (SettingItemView) view.findViewById(R.id.view_explain);
        mViewExplain.setOnClickListener(mOnClickListener);
        mViewLock = (SettingItemView) view.findViewById(R.id.view_lock);
        mViewLock.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_back:
                    onBackPressed();
                    break;
                //清理数据库的设备信息
                case R.id.view_clear:
                    CustomDialogFactory.showConfirmDialog(getActivity(), false, DIALOG_TITLE, DIALOG_CONTENT,"我要删","算了",
                            mPositiveClickListener, mNegativeClickListener, null);
                    break;

                //我的设备
                case R.id.view_equip:
                    MyEquipActivity.launch(getActivity());
                    break;

                //使用说明
                case R.id.view_explain:
                    CustomDialogFactory.showExplainDialog(getActivity(), false, mOnDialogClickListener, true);
                    break;
                //意见反馈
                case R.id.view_feedback:
                    FeedbackActivity.launch(getActivity());
                    break;
                //当前版本
                case R.id.view_version:
                    ToastUtil.showBottom(getActivity(), getString(R.string.current_version));
                    break;
                //安全设置
                case R.id.view_lock:
//                    LockActivity.Launch(getActivity(), LockActivity.TYPE_SETTING);
                    SettingLockActivity.Launch(getActivity());
                    break;

                default:
                    break;
            }
        }
    };

    private OnDialogClickListener mOnDialogClickListener = new OnDialogClickListener() {
        @Override
        public void onClick(AlertDialog dialog) {
            dialog.dismiss();
        }
    };

    private OnDialogClickListener mPositiveClickListener = new OnDialogClickListener() {
        @Override
        public void onClick(AlertDialog dialog) {
            mEquipDataPresenter = EquipDataPresenter.getInstance();
            mEquipDataPresenter.initDbHelp(getActivity());
            mEquipDataPresenter.deleteAllData();
        }
    };

    private OnDialogClickListener mNegativeClickListener = new OnDialogClickListener() {
        @Override
        public void onClick(AlertDialog dialog) {
            dialog.dismiss();

        }
    };


    @Override
    public boolean onBackPressed() {
        if(isAdded()){
            ((HomeActivity) getActivity()).showTab(0);
            return true;
        }
        return super.onBackPressed();
    }
}
