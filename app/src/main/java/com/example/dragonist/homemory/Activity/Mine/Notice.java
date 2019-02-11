package com.example.dragonist.homemory.Activity.Mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dragonist.homemory.Bean.MessageBean;
import com.example.dragonist.homemory.Bean.UserInfo;
import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.Post2Server;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Notice extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private Handler handler;
    private TextView tvReturn;
    private ListView lvMessage;
    private ArrayList<MessageBean> messages;
    private NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        tvReturn = findViewById(R.id.bt_return);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvMessage = findViewById(R.id.lvMessage);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 200) {
                    messages = (ArrayList<MessageBean>) msg.obj;
                    noticeAdapter = new NoticeAdapter(getBaseContext(), messages);
                    lvMessage.setAdapter(noticeAdapter);
                }
            }
        };

        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = null;
        try {
            requestBody = builder.addFormDataPart("account", URLEncoder.encode(getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
                    .addFormDataPart("method", "receive")
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
                        Log.e("12", result);
                        JsonObject object = (JsonObject) new JsonParser().parse(result);
                        int status = object.get("status").getAsInt();
                        Message msg = new Message();
                        msg.what = status;
                        ArrayList<MessageBean> messages = new ArrayList<>();
                        if (status == 200) {
                            int amount = object.get("amount").getAsInt();
                            for (int i = 0; i < amount; i++) {
                                JsonObject object1 = object.get(i + "").getAsJsonObject();
                                String type = object1.get("type").getAsString();
                                String senderName = URLDecoder.decode(object1.get("senderName").getAsString(),"UTF-8");
                                String receiverName = URLDecoder.decode(object1.get("receiverName").getAsString(),"UTF-8");
                                String familyName = URLDecoder.decode(object1.get("familyName").getAsString(),"UTF-8");
                                String timestamp = object1.get("timestamp").getAsString();
                                int id = object1.get("id").getAsInt();
                                messages.add(new MessageBean(type, senderName, receiverName, familyName, timestamp, id));
                            }
                        }
                        msg.obj = messages;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
}
