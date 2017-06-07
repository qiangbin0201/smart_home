package com.smart.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.smart.home.R;
import com.smart.home.View.LockPatternView;
import com.smart.home.presenter.StatusPresenter;

public class LockActivity extends Activity implements LockPatternView.OnPatternChangeListener {

    private TextView mLockPatternHint;

    private LockPatternView mLockPatternView;

    private Button mContinueButton, mCancelButton;

    public static final String PASSWORD_FILE = "password_file";

    public static final String PASSWORD = "password";
    public static final String TYPE_SETTING = "setting";
    public static final String TYPE_CHECK = "check";
    public static final String ARG_TYPE = "type";

    private static final int BUTTON_NO_ENABLED = 0xFF777777;

    private StatusPresenter mPasswordPresenter;

    private String type;

    private String patternPassword;

    private String patternPassword1;

    private boolean isConfirm = false;
    //标识判别作用，辅助参数
    private boolean flag = true;

    protected SoundPool mSoundPool;

    protected int mSound;

    protected AudioManager mgr;

    public static void Launch(Context context, String tag) {
        Intent intent = new Intent(context, LockActivity.class);
        intent.putExtra(ARG_TYPE, tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setContentView(R.layout.activity_lock);

        mPasswordPresenter = StatusPresenter.getInstance(this, PASSWORD_FILE);

        initView();

        initKeyTone();

    }

    private void initView() {

        mLockPatternHint = (TextView) findViewById(R.id.lock_pattern_hint);
        mLockPatternView = (LockPatternView) findViewById(R.id.lock_pattern_view);
        mLockPatternView.setOnPatternChangeListener(this);
        LinearLayout mMenuLinearLatout = (LinearLayout) findViewById(R.id.ll_menu);
        mCancelButton = (Button) findViewById(R.id.btn_cancel);
        mCancelButton.setOnClickListener(mOnClickListener);
        mContinueButton = (Button) findViewById(R.id.btn_continue);
        mContinueButton.setOnClickListener(mOnClickListener);

        type = getIntent().getStringExtra(ARG_TYPE);

        if (type != null && type.equals(TYPE_SETTING)) {
            mMenuLinearLatout.setVisibility(View.VISIBLE);
            mContinueButton.setEnabled(false);
            mContinueButton.setTextColor(BUTTON_NO_ENABLED);
        }
    }

    private void initKeyTone() {
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        mSound = mSoundPool.load(this, R.raw.lock, 1);
        mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void initLockPatternView() {
        mLockPatternView.setOnPatternChangeListener(this);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    if (isConfirm && flag) {
                        mLockPatternView.resetPoint();
                        mContinueButton.setEnabled(false);
                        mContinueButton.setTextColor(BUTTON_NO_ENABLED);
                        mCancelButton.setText(R.string.cancel);
                        isConfirm = false;
                    } else {
                        finish();
                    }
                    break;
                case R.id.btn_continue:
                    if (isConfirm) {
                        mLockPatternView.resetPoint();
                        patternPassword1 = patternPassword;
                        mContinueButton.setText(R.string.confirm);
                        mContinueButton.setEnabled(false);
                        mContinueButton.setTextColor(BUTTON_NO_ENABLED);
                        mCancelButton.setText(R.string.cancel);
                        mLockPatternHint.setText(R.string.please_draw_pattern_confirm);
                        flag = false;

                        initLockPatternView();
                    } else {
                        mPasswordPresenter.putString(PASSWORD, patternPassword);
                        finish();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onPatternChange(String patternPassword) {
        this.patternPassword = patternPassword;
        if (patternPassword == null) {
            vibrate();
            mLockPatternHint.setText(R.string.limit_five_point);
        } else {
            if (type != null && type.equals(TYPE_CHECK)) {
                if (patternPassword.equals(mPasswordPresenter.getString(PASSWORD, ""))) {
                    //获得当前媒体音量用来设置按键音大小
                    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
                    mSoundPool.play(mSound, streamVolumeCurrent, streamVolumeCurrent, 1, 0, 1);
                    HomeActivity.launch(this);
                    finish();
                } else {
                    vibrate();
                    mLockPatternHint.setText(R.string.password_error);
                    mLockPatternView.resetPoint();
                }
            }
            if (isConfirm) {
                if (patternPassword1 != null) {
                    if (patternPassword1.equals(patternPassword)) {
                        mContinueButton.setText(R.string.confirm);
                        mContinueButton.setTextColor(Color.BLACK);
                        mContinueButton.setEnabled(true);
                        mLockPatternHint.setText(R.string.press_confirm_finish_setting);
                        isConfirm = false;
                        flag = false;
                    } else {
                        mLockPatternView.resetPoint();
                        mLockPatternHint.setText(R.string.please_try_again);
                    }
                } else {
                    mLockPatternHint.setText(R.string.please_press_continue);
                }
            }
            if (type != null && type.equals(TYPE_SETTING)) {
                if (!isConfirm && flag) {
                    mContinueButton.setEnabled(true);
                    mContinueButton.setText(R.string.please_continue);
                    mContinueButton.setTextColor(Color.BLACK);
                    mCancelButton.setText(R.string.try_again);
                    mLockPatternHint.setText(R.string.please_press_continue);
                    isConfirm = true;
                }
            }
        }

    }

    @Override
    public void onPatternStarted(boolean isStarted) {
        if (isStarted) {
            mLockPatternHint.setText(R.string.please_draw_pattern);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (type.equals(TYPE_CHECK)) {
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }
}
