package com.smart.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import com.smart.home.R;
import com.smart.home.model.ToolbarStyle;
import com.smart.home.presenter.FeedbackPresenter;
import com.smart.home.utils.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by lenovo on 2017/4/25.
 */

public class FeedbackActivity extends BaseActivity {

    private static final String TOOLBAR_TITLE = "意见反馈";

    private EditText mContentEditText;

    private EditText mContactEditText;

    private Button mSubmitButton;

    public static void launch(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);
        setContentView(R.layout.activity_feedback);

        initView();


    }

    private void initView() {
        mContactEditText = (EditText) findViewById(R.id.et_feedback_contact);
        mContentEditText = (EditText) findViewById(R.id.et_feedback_content);
        mSubmitButton = (Button) findViewById(R.id.btn_submit);
        mSubmitButton.setOnClickListener(mOnClickListener);

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_submit:
                    if(check()){
                        feedback(mContentEditText.getText().toString(), mContactEditText.getText().toString());
                    }

            }


        }
    };

    private boolean check() {
        String content = mContentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showBottom(this, getString(R.string.feedback_input_content_tip));
            return false;
        }

        return true;
    }

    private void feedback(String content, String mobile) {
        FeedbackPresenter presenter = new FeedbackPresenter();
        addSubscription(presenter.feedback(content, mobile).observeOn(AndroidSchedulers.mainThread()).subscribe(
                r -> {

                    ToastUtil.showBottom(this, getString(R.string.feedback_success));
                    finish();
                },
                e -> {
                    ToastUtil.showBottom(this, getString(R.string.feedback_fail));
                }
        ));
    }
}
