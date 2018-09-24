package com.example.dragonist.homemory.Activity.Mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.dragonist.homemory.Adapter.SettingAdapter;
import com.example.dragonist.homemory.R;

public class Setting extends AppCompatActivity {
    private ListView lv_categorys;
    private Button bt_return;
    private SettingAdapter settingAdapter;
    private String[] categorys = {"账号管理","账号与安全","修改个人资料","消息设置","隐私设置"};

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

        bt_return = findViewById(R.id.bt_return);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
