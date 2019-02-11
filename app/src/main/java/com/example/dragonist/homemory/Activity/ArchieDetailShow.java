package com.example.dragonist.homemory.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.Bean.MemoryBean;
import com.example.dragonist.homemory.R;

public class ArchieDetailShow extends AppCompatActivity {
    private TextView tvReturn;
    private TextView Location;
    private TextView Relationship;
    private TextView Description;
    private TextView uploadTime;
    private ImageView Image;
    private ImageView Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archie_detail_show);
        Location = findViewById(R.id.tvLocation);
        Relationship = findViewById(R.id.tvrelationship);
        Description = findViewById(R.id.tvDescription);
        uploadTime = findViewById(R.id.tvUploadTime);
        Image = findViewById(R.id.tvIcon);
        Icon = findViewById(R.id.icon);
        MemoryBean memoryBean = (MemoryBean) getIntent().getParcelableExtra("information");
        Log.e("测试详细信息", "怎么回事" + memoryBean.getBase64Image() + memoryBean.getLocation());
        Bitmap bitmap = BitmapFactory.decodeFile(memoryBean.getPath());
        Image.setImageBitmap(bitmap);
        Icon.setImageResource(R.drawable.portrait);
        Location.setText(memoryBean.getLocation());
        Relationship.setText(memoryBean.getRelationship());
        Description.setText(memoryBean.getDescription());
        uploadTime.setText(memoryBean.getUploadTime());
        tvReturn = findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
