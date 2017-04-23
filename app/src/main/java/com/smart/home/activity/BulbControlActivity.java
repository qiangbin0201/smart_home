package com.smart.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.smart.home.R;
import com.smart.home.model.ToolbarStyle;



/**
 * Created by lenovo on 2017/4/20.
 */

public class BulbControlActivity extends BaseActivity {

    private static final String TOOLBAR_TITLE = "电灯";

    private Spinner spinner;

    private String mSchema;

    private static final String SCHEMA = "schema";

    public static void Launch(Context context, String schema) {
        Intent intent = new Intent(context, BulbControlActivity.class);
        intent.putExtra(SCHEMA, schema);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.push_down_in, R.anim.anim_alpha_dismiss);
        setToolbar(ToolbarStyle.RETURN_TITLE, TOOLBAR_TITLE);
        setContentView(R.layout.activity_bulb);
        initData();
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] arr = { "孙悟空", "猪八戒", "唐僧" };
        // 创建ArrayAdapter对象
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.view_spinner_item, arr);
        // 为Spinner设置Adapter
        spinner.setAdapter(adapter);

    }

    private void initData() {
        mSchema = getIntent().getStringExtra(SCHEMA);
    }
}


