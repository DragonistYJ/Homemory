package com.example.dragonist.homemory.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.Post2Server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Login extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private Handler handler;
    private EditText et_accout;
    private EditText et_password;
    private TextView tv_login;
    private TextView tvRegister;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private String account = null;
    private String password = null;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String)msg.obj;
                if (result.equals("ok")) {
                    sp = getSharedPreferences("HOMEMORY",MODE_PRIVATE);
                    editor = sp.edit();
                    editor.putString("account",account);
                    editor.putString("password",password);
                    editor.commit();
                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                }
            }
        };

        sp = getSharedPreferences("HOMEMORY",MODE_PRIVATE);
        account = sp.getString("account",account);
        password = sp.getString("password",password);
        if (account!=null && password!=null) login();

        setContentView(R.layout.activity_login);
        init();
    }

    //判断帐号密码是否正确
    private void login() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = null;
        try {
            requestBody = builder.addFormDataPart("account", URLEncoder.encode(account,"UTF-8"))
                    .addFormDataPart("password",URLEncoder.encode(password,"UTF-8"))
                    .setType(MultipartBody.FORM).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Post2Server post = new Post2Server("http://118.25.210.13:8080/Homemory/Login", requestBody, handler);
        new Thread(post).start();
    }

    private void init(){
        et_accout = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = et_accout.getText().toString();
                password = et_password.getText().toString();
                if (!account.isEmpty() && !password.isEmpty()) login();
                else Toast.makeText(Login.this, "请输入正确的账号和密码", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(Login.this, Main.class);
                startActivity(intent);*/
            }
        });

        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Regist1.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
