package com.example.dragonist.homemory.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dragonist.homemory.R;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

public class Regist1 extends AppCompatActivity {
    private Button btn_code;
    private EditText et_phone;
    private EditText et_SMSCode;
    private Button btn_register;

    private String phone;
    private String SMSCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist1);
        init();

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString();
                if (et_phone.getText().toString().isEmpty()) {
                    Toast.makeText(Regist1.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.getInstance().getSmsCodeAsyn(phone, "1", new SmscodeListener() {
                        @Override
                        public void getCodeSuccess(String s) {
                            Toast.makeText(Regist1.this, "发送成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void getCodeFail(int i, String s) {
                            Toast.makeText(Regist1.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSCode = et_SMSCode.getText().toString();
                SMSSDK.getInstance().checkSmsCodeAsyn(phone, SMSCode, new SmscheckListener() {
                    @Override
                    public void checkCodeSuccess(String s) {
                        Intent intent = new Intent(Regist1.this, Register2.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void checkCodeFail(int i, String s) {
                        Toast.makeText(Regist1.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void init() {
        btn_code = findViewById(R.id.btn_code);
        et_phone = findViewById(R.id.et_phone);
        et_SMSCode = findViewById(R.id.et_SMSCode);
        btn_register = findViewById(R.id.btn_register);
        SMSSDK.getInstance().initSdk(this);
    }
}
