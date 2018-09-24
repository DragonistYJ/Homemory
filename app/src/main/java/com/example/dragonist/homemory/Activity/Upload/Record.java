package com.example.dragonist.homemory.Activity.Upload;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dragonist.homemory.R;

public class Record extends AppCompatActivity {
    private ImageView iv_category;
    private ImageView iv_authority;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
    }

    private void init(){
        iv_category = findViewById(R.id.iv_category);
        iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Record.this, Category.class);
                startActivityForResult(intent,0x1111);
            }
        });

        iv_authority = findViewById(R.id.iv_authority);
        iv_authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Record.this, Authority.class);
                startActivityForResult(intent, 0x1111);
            }
        });
    }
}
