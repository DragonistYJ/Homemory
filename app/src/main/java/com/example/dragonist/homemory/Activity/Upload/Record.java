package com.example.dragonist.homemory.Activity.Upload;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.UploadToosPackage.UploadDescription;
import com.example.dragonist.homemory.UploadToosPackage.UploadFile;
import com.example.dragonist.homemory.UploadToosPackage.UploadTools;

import java.io.InputStream;

public class Record extends AppCompatActivity {
    private UploadFile uploadFile;
    private Intent intent;
    private UploadDescription uploadDescription;
    private ImageView iv_cancel;
    private ImageView iv_send;
    private ImageView iv_share;
    private ImageView iv_thumbnail;
    private ImageView iv_category;
    private ImageView iv_authority;
    private EditText ie_category;
    private EditText ie_label;
    private EditText ie_who;
    private EditText ie_location;
    private EditText ie_authority;
    private EditText ie_keyWord;
    private EditText ie_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        intent=getIntent();
        uploadFile=(UploadFile) intent.getSerializableExtra("1");
        uploadDescription=(UploadDescription)intent.getSerializableExtra("2");
        uploadFile.setDescription(uploadDescription);
        init();
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUiInformation();
                Log.e("23",uploadFile.getDescription().getDescription());
                UploadTools.postInformation(uploadFile,"http://118.25.210.13:8080/Homemory/Upload");
                finish();
            }
        });
    }
    public void init(){
        //给UI分配对象
        ie_category=(EditText)findViewById(R.id.et_category);
        ie_label=(EditText)findViewById(R.id.et_label);
        ie_who=(EditText)findViewById(R.id.et_who);
        ie_location=(EditText)findViewById(R.id.et_location);
        ie_authority=(EditText)findViewById(R.id.et_authority);
        ie_keyWord=(EditText)findViewById(R.id.et_keyword);
        ie_description=(EditText)findViewById(R.id.et_description);
        iv_send=(ImageView)findViewById(R.id.send);
        iv_thumbnail=(ImageView)findViewById(R.id.thumbnail);

        iv_cancel=(ImageView)findViewById(R.id.cancel);
        switch (intent.getStringExtra("Type")){
            case "IMAGE":
                Bitmap bitmap= BitmapFactory.decodeFile(uploadFile.getFile().getPath());
                iv_thumbnail.setImageBitmap(bitmap);
                break;
            case "MUSIC":
                @SuppressLint("ResourceType") InputStream inputStream=getResources().openRawResource(R.drawable.a);
                Bitmap bitmap1=BitmapFactory.decodeStream(inputStream);
                iv_thumbnail.setImageBitmap(bitmap1);
                break;
            case "VIDEO":
                @SuppressLint("ResourceType") InputStream inputStream1=getResources().openRawResource(R.drawable.a);
                Bitmap bitmap2=BitmapFactory.decodeStream(inputStream1);
                iv_thumbnail.setImageBitmap(bitmap2);
                break;
            case "DOCUMENT":
                @SuppressLint("ResourceType") InputStream inputStream3=getResources().openRawResource(R.drawable.a);
                Bitmap bitmap3=BitmapFactory.decodeStream(inputStream3);
                iv_thumbnail.setImageBitmap(bitmap3);
                break;
        }

        iv_category = findViewById(R.id.iv_category);
        iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Record.this,Category.class);
                startActivityForResult(intent,55);
            }
        });

        iv_authority = findViewById(R.id.iv_authority);
        iv_authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Record.this,Authority.class);
                startActivityForResult(intent,0x404);
            }
        });
    }
    public void getUiInformation(){
        uploadFile.getDescription().setLabel(ie_label.getText().toString());
        uploadFile.getDescription().setDescription(ie_description.getText().toString());
        uploadFile.getDescription().setAboutPeople(ie_who.getText().toString());
        uploadFile.getDescription().setKeyWord(ie_keyWord.getText().toString());
        uploadFile.getDescription().setLocation(ie_location.getText().toString());
        uploadFile.setLabel(ie_category.getText().toString());
        uploadFile.setRange(ie_authority.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==55 && resultCode==56) {
            String category = data.getStringExtra("category");
            ie_category.setText(category);
        }
    }
}
