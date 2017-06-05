package com.smart.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.smart.home.R;
import com.smart.home.View.LockPatternView;
import com.smart.home.View.SettingItemView;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.StatusPresenter;

public class SettingLockActivity extends BaseActivity implements LockPatternView.OnPatternChangeListener{

    private LockPatternView mLockPatternView;

    private TextView mLockPatternHint;

    private LinearLayout mSettingLinearLayout;

    private SettingItemView mViewFix, mViewClear;

    private StatusPresenter mPasswordPresenter;

    private static final String TOOLBAR_TITLE = "安全设置";

    public static void Launch(Context context){
        Intent intent = new Intent(context, SettingLockActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.rotate90, R.anim.push_down_in);
        setToolbar(ToolbarStyle.RETURN_TITLE_ICON, TOOLBAR_TITLE);
        setContentView(R.layout.activity_setting_lock);

        mPasswordPresenter = StatusPresenter.getInstance(this, LockActivity.PASSWORD_FILE);

        initView();

    }

    private void initView() {
        mLockPatternHint = (TextView) findViewById(R.id.lock_pattern_hint);
        mLockPatternView = (LockPatternView) findViewById(R.id.lock_pattern_view);
        mLockPatternView.setOnPatternChangeListener(this);
        mSettingLinearLayout = (LinearLayout) findViewById(R.id.ll_setting);
        mViewClear = (SettingItemView) findViewById(R.id.view_clear_password);
        mViewClear.setOnClickListener(mOnClickListener);
        mViewFix = (SettingItemView) findViewById(R.id.view_fix_password);
        mViewFix.setOnClickListener(mOnClickListener);
        String mPassword = mPasswordPresenter.getString(LockActivity.PASSWORD,"");
        if(TextUtils.isEmpty(mPassword)){
            mLockPatternView.setVisibility(View.GONE);
            mLockPatternHint.setVisibility(View.GONE);
            mSettingLinearLayout.setVisibility(View.VISIBLE);

        }

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.view_fix_password:
                    LockActivity.Launch(SettingLockActivity.this, LockActivity.TYPE_SETTING);
                    finish();
                    break;
                case R.id.view_clear_password:
                    mPasswordPresenter.putString(LockActivity.PASSWORD, "");
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onPatternChange(String patternPassword) {
        if(patternPassword!= null &&patternPassword.equals(mPasswordPresenter.getString(LockActivity.PASSWORD, ""))){
            mLockPatternView.setVisibility(View.GONE);
            mLockPatternHint.setVisibility(View.GONE);
            mSettingLinearLayout.setVisibility(View.VISIBLE);
        }else {
            mLockPatternHint.setText(R.string.password_error_and_try_again);
            mLockPatternView.resetPoint();
        }

    }

    @Override
    public void onPatternStarted(boolean isStarted) {

    }
}

