package com.example.dragonist.homemory.Activity.Mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dragonist.homemory.Activity.Login;
import com.example.dragonist.homemory.Adapter.SettingAdapter;
import com.example.dragonist.homemory.R;

public class Setting extends AppCompatActivity {
    private ListView lv_categorys;
    private TextView tvReturn;
    private TextView tvSignOut;

    private SettingAdapter settingAdapter;
    private String[] categorys = {"账号管理","账号与安全","修改个人资料","消息设置","隐私设置"};
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        lv_categorys = findViewById(R.id.lv_categorys);
        settingAdapter = new SettingAdapter(this,categorys);
        lv_categorys.setAdapter(settingAdapter);

        tvReturn = findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSignOut = findViewById(R.id.tvSignOut);
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("HOMEMORY",MODE_PRIVATE);
                editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent login = new Intent(Setting.this, Login.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            }
        });
    }
}
