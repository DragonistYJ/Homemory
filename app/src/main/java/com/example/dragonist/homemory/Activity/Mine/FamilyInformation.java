package com.example.dragonist.homemory.Activity.Mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dragonist.homemory.Activity.Family.FamilyGroup;
import com.example.dragonist.homemory.Adapter.FamilyMemberAdapter;
import com.example.dragonist.homemory.Bean.UserInfo;
import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.FamilyMember;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FamilyInformation extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private String familyName;
    private int amount;
    private FamilyMemberAdapter familyMemberAdapter;
    private ArrayList<FamilyMember> familyMembers = new ArrayList<FamilyMember>();
    private Handler handler;

    private TextView bt_return;
    private TextView tvFamilyName;
    private ListView lv_familyMember;
    private TextView tvChargeArchive;
    private TextView tvInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_information);
        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 200:
                        ArrayList<UserInfo> userInfos = (ArrayList<UserInfo>) msg.obj;
                        for (int i = 0; i < userInfos.size(); i++) {
                            FamilyMember familyMember = new FamilyMember(userInfos.get(i).getPortrait(), false, userInfos.get(i).getNickName(), userInfos.get(i).getBirthday(), getSharedPreferences("HOMEMORY", MODE_PRIVATE).getBoolean("Administrator", true));
                            familyMembers.add(familyMember);
                        }
                        familyMemberAdapter = new FamilyMemberAdapter(getBaseContext(), familyMembers);
                        lv_familyMember.setAdapter(familyMemberAdapter);
                        tvFamilyName.setText(familyName + "（" + amount + "）");
                }
            }
        };
    }

    private void init() {
        tvFamilyName = findViewById(R.id.familyName);
        //返回按钮
        bt_return = findViewById(R.id.bt_return);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //家庭成员列表
        lv_familyMember = findViewById(R.id.lv_familyMember);
        //布局里面有按钮会抢焦点，需要去除
        lv_familyMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                familyMembers.get(position).setExpend(!familyMembers.get(position).isExpend());
                familyMemberAdapter.setFamilyMembers(familyMembers);
                familyMemberAdapter.notifyDataSetChanged();
            }
        });

        tvChargeArchive = findViewById(R.id.tvChargeArchive);
        tvInvite = findViewById(R.id.tvInvite);
        if (!getSharedPreferences("HOMEMORY", MODE_PRIVATE).getBoolean("Administrator", false)) {
            tvChargeArchive.setVisibility(View.INVISIBLE);
            tvInvite.setVisibility(View.INVISIBLE);
        }

        /*
        跳转至邀请新成员的界面
         */
        tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyInformation.this, FamilyGroup.class);
                intent.putExtra("aim", "invite");
                startActivity(intent);
            }
        });


        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = null;
        try {
            requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                    .addFormDataPart("method", URLEncoder.encode("show", "UTF-8"))
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
                            amount = object.get("amount").getAsInt();
                            familyName = URLDecoder.decode(object.get("familyName").getAsString(), "UTF-8");
                            ArrayList<UserInfo> userInfos = new ArrayList<>();
                            for (int i = 0; i < amount; i++) {
                                JsonObject object1 = object.get(i + "").getAsJsonObject();
                                String nickName = URLDecoder.decode(object1.get("nickName").getAsString(), "UTF-8");
                                String birthday = URLDecoder.decode(object1.get("birthday").getAsString(), "UTF-8");
                                byte[] bytes = Base64.decode(object1.get("portrait").getAsString(), android.util.Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                UserInfo userInfo = new UserInfo(nickName, birthday, bitmap);
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
}

