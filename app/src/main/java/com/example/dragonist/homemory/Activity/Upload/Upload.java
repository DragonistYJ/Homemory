package com.example.dragonist.homemory.Activity.Upload;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dragonist.homemory.R;

public class Upload extends AppCompatActivity {
    private ImageView iv_cancel;
    private ImageView iv_record;
    private ImageView iv_timecapsule;
    private ImageView iv_keeptime;
    private ImageView iv_picture;
    private ImageView iv_video;
    private ImageView iv_music;
    private ImageView iv_document;
    private LinearLayout ll_leftbox;
    private LinearLayout ll_rightbox;

    private boolean left_click = false;
    private boolean right_click = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String method = this.getIntent().getExtras().getString("method");
        switch (method) {
            case "local":
                init_local();
                init_all();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init_local() {
        setContentView(R.layout.activity_upload_local);
    }

    private void init_all(){
        iv_record = findViewById(R.id.record);
        iv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Upload.this,Record.class);
                startActivityForResult(intent,0x0001);
            }
        });
        ll_leftbox = findViewById(R.id.left_box);
        ll_leftbox.setVisibility(View.INVISIBLE);
        ll_rightbox = findViewById(R.id.right_box);
        ll_rightbox.setVisibility(View.INVISIBLE);
        iv_timecapsule = findViewById(R.id.timecapsule);
        iv_timecapsule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!left_click) {
                    ll_leftbox.setVisibility(View.VISIBLE);
                    left_click = true;
                } else {
                    ll_leftbox.setVisibility(View.INVISIBLE);
                    left_click = false;
                }
            }
        });
        iv_keeptime = findViewById(R.id.deadline);
        iv_keeptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!right_click) {
                    ll_rightbox.setVisibility(View.VISIBLE);
                    right_click = true;
                } else {
                    ll_rightbox.setVisibility(View.INVISIBLE);
                    right_click = false;
                }
            }
        });
    }
}
