package com.example.dragonist.homemory.Activity.Upload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dragonist.homemory.R;

public class Authority extends AppCompatActivity {
    private RelativeLayout rl_publish;
    private RelativeLayout rl_personal;
    private ImageView iv_publish;
    private ImageView iv_personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);
        init();
    }

    private void init() {
        iv_publish = findViewById(R.id.iv_publish);
        iv_personal = findViewById(R.id.iv_personal);
        rl_publish = findViewById(R.id.rl_publish);
        rl_personal = findViewById(R.id.rl_personal);

        rl_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_publish.setImageResource(R.drawable.upload_checked);
                iv_personal.setImageResource(R.drawable.upload_unchecked);
            }
        });

        rl_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_personal.setImageResource(R.drawable.upload_checked);
                iv_publish.setImageResource(R.drawable.upload_unchecked);
            }
        });
    }
}
