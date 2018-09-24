package com.example.dragonist.homemory.Utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Post2Server implements Runnable {
    OkHttpClient client = new OkHttpClient();
    private String ip;
    private RequestBody requestBody;
    private Handler handler;
    private String result;

    public Post2Server(String ip, RequestBody requestBody, Handler handler) {
        this.ip = ip;
        this.requestBody = requestBody;
        this.handler = handler;
    }

    @Override
    public void run() {
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(ip).post(requestBody).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result = "connect to server failed";
                Message msg = new Message();
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
                result = URLDecoder.decode(result,"utf-8");
                Message msg = new Message();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }


}
