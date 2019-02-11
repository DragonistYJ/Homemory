package com.example.dragonist.homemory.Activity.Family;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dragonist.homemory.Bean.UserInfo;
import com.example.dragonist.homemory.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FamilyGroup extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private ArrayList<UserInfo> userInfos;
    private Handler handler;
    private Button btnReturn;
    //create
    private EditText etFamilyName;
    private TextView tvFamilyNumber;
    private Button btnConfirm;
    //join
    private EditText etFamilyNumber;
    private TextView tvFamilyName;
    private Button btnSearch;
    private ListView lvMember;
    private MemberAdapter memberAdapter;
    //invite
    private EditText etSearch;
    private ListView lvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        tvFamilyNumber.setText((String) msg.obj);
                        break;
                    case 2:
                        setResult(0x0010);
                        finish();
                        break;
                    case 404:
                        tvFamilyName.setText("该家庭名称不存在");
                        break;
                    case 405:
                        tvFamilyName.setText("家庭名称与家庭编号不匹配");
                        break;
                    case 200:
                        userInfos = (ArrayList<UserInfo>) msg.obj;
                        tvFamilyName.setText(etFamilyName.getText().toString() + "（" + userInfos.size() + "）");
                        memberAdapter = new MemberAdapter(getBaseContext(), userInfos);
                        memberAdapter.setAccount(getSharedPreferences("HOMEMORY",MODE_PRIVATE).getString("account",""));
                        lvMember.setAdapter(memberAdapter);
                        break;
                    case 500:
                        finish();
                        break;
                    case 300:
                        userInfos = (ArrayList<UserInfo>) msg.obj;
                        memberAdapter = new MemberAdapter(getBaseContext(),userInfos);
                        memberAdapter.setHasButton(true);
                        memberAdapter.setAccount(getSharedPreferences("HOMEMORY",MODE_PRIVATE).getString("account",""));
                        lvResult.setAdapter(memberAdapter);
                        break;
                }
            }
        };

        String aim = getIntent().getExtras().getString("aim");
        switch (aim) {
            case "create":
                setContentView(R.layout.activity_family_create);
                init();
                init_create();
                break;
            case "join":
                setContentView(R.layout.activity_family_join);
                init();
                init_join();
                break;
            case "invite":
                setContentView(R.layout.activity_family_invite);
                init();
                init_invite();
                break;
        }

    }

    private void init() {
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
    创建家庭界面的初始化
     */
    private void init_create() {
        etFamilyName = findViewById(R.id.etFamilyName);
        etFamilyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = etFamilyName.getText().toString();
                if (value.isEmpty()) return;
                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                            .addFormDataPart("method", URLEncoder.encode("check", "UTF-8"))
                            .addFormDataPart("information", URLEncoder.encode(value, "UTF-8"))
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Family").post(requestBody).build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = URLDecoder.decode(result, "UTF-8");
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }).start();
            }
        });

        tvFamilyNumber = findViewById(R.id.tvFamilyNumber);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = tvFamilyNumber.getText().toString();
                if (id.equals("该家庭名称已存在") || id.isEmpty()) return;
                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                            .addFormDataPart("method", URLEncoder.encode("create", "UTF-8"))
                            .addFormDataPart("id", URLEncoder.encode(id, "UTF-8"))
                            .addFormDataPart("familyName", URLEncoder.encode(etFamilyName.getText().toString(), "UTF-8"))
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Family").post(requestBody).build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Message msg = new Message();
                                msg.what = 2;
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /*
    加入家庭界面的初始化
     */
    private void init_join() {
        etFamilyName = findViewById(R.id.etFamilyName);
        etFamilyNumber = findViewById(R.id.etFamilyNumber);
        tvFamilyName = findViewById(R.id.tvFamilyName);
        btnSearch = findViewById(R.id.btnSearchFamily);
        lvMember = findViewById(R.id.lvMember);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyName = etFamilyName.getText().toString();
                String familyNumber = etFamilyNumber.getText().toString();
                if (familyName.isEmpty() || familyNumber.isEmpty()) {
                    tvFamilyName.setText("请输入家庭名称与家庭编号");
                    return;
                }

                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                            .addFormDataPart("method", URLEncoder.encode("seek", "UTF-8"))
                            .addFormDataPart("familyName", URLEncoder.encode(familyName, "UTF-8"))
                            .addFormDataPart("familyNumber", URLEncoder.encode(familyNumber, "UTF-8"))
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Family").post(requestBody).build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                JsonObject object = (JsonObject) new JsonParser().parse(result);
                                int status = object.get("status").getAsInt();
                                Message msg = new Message();
                                msg.what = status;
                                if (status == 200) {
                                    int amount = object.get("amount").getAsInt();
                                    ArrayList<UserInfo> userInfos = new ArrayList<>();
                                    for (int i = 0; i < amount; i++) {
                                        JsonObject object1 = object.get(i + "").getAsJsonObject();
                                        String nickName = URLDecoder.decode(object1.get("nickName").getAsString(), "UTF-8");
                                        byte[] bytes = Base64.decode(object1.get("portrait").getAsString(), android.util.Base64.DEFAULT);
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        UserInfo userInfo = new UserInfo(nickName, bitmap);
                                        userInfos.add(userInfo);
                                    }
                                    msg.obj = userInfos;
                                }
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }).start();
            }
        });

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyName = etFamilyName.getText().toString();
                String familyNumber = etFamilyNumber.getText().toString();
                if (familyName.isEmpty() || familyNumber.isEmpty()) {
                    tvFamilyName.setText("请输入家庭名称与家庭编号");
                    return;
                }

                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                            .addFormDataPart("method", URLEncoder.encode("apply", "UTF-8"))
                            .addFormDataPart("familyName", URLEncoder.encode(familyName, "UTF-8"))
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Message").post(requestBody).build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                Log.e("1213",result);
                                Message msg = new Message();
                                if (result.equals("ok")) {
                                    msg.what = 500;
                                }
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /*
    邀请新成员界面的初始化
     */
    private void init_invite() {
        etSearch = findViewById(R.id.etSearch);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String context  = etSearch.getText().toString();
                if (context.isEmpty()) return;

                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                            .addFormDataPart("method", URLEncoder.encode("seekMember", "UTF-8"))
                            .addFormDataPart("context", URLEncoder.encode(context, "UTF-8"))
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Family").post(requestBody).build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                JsonObject object = (JsonObject) new JsonParser().parse(result);
                                int status = object.get("status").getAsInt();
                                Message msg = new Message();
                                msg.what = status;
                                if (status == 300) {
                                    int amount = object.get("amount").getAsInt();
                                    ArrayList<UserInfo> userInfos = new ArrayList<>();
                                    for (int i = 0; i < amount; i++) {
                                        JsonObject object1 = object.get(i + "").getAsJsonObject();
                                        String nickName = URLDecoder.decode(object1.get("nickName").getAsString(), "UTF-8");
                                        byte[] bytes = Base64.decode(object1.get("portrait").getAsString(), android.util.Base64.DEFAULT);
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        UserInfo userInfo = new UserInfo(nickName, bitmap);
                                        userInfos.add(userInfo);
                                    }
                                    msg.obj = userInfos;
                                }
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }).start();
            }
        });

        lvResult = findViewById(R.id.lvResult);
    }
}
