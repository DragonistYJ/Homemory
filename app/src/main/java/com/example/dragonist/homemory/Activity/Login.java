package com.example.dragonist.homemory.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.Post2Server;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Login extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private Handler handler;
    private EditText et_accout;
    private EditText et_password;
    private TextView tv_login;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String)msg.obj;
                if (result.equals("ok")) {
                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                }
            }
        };


    }

    //判断帐号密码是否正确
    private void accout_check() {
        String accout = et_accout.getText().toString();
        String password = et_password.getText().toString();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = null;
        requestBody = builder.addFormDataPart("account",accout)
                .addFormDataPart("password",password)
                .setType(MultipartBody.FORM).build();
        Post2Server post = new Post2Server("http://10.0.2.2:8080/Homemory/Login", requestBody, handler);
        new Thread(post).start();
    }

    private void init(){
        et_accout = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accout_check();
                Intent intent = new Intent(Login.this,Main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
