package com.smart.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.*;

import com.smart.home.R;
import com.smart.home.model.ToolbarStyle;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2017/4/20.
 */

public class BulbControlActivity extends BaseActivity {

    private static final String TOOLBAR_TITLE = "电灯";

    public static void Launch(Context context) {
        Intent intent = new Intent(context, BulbControlActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);
        setContentView(R.layout.activity_bulb);
    }
}


