package com.example.dragonist.homemory.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.dragonist.homemory.Bean.Archive;
import com.example.dragonist.homemory.BitmapAndType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchCallableDate implements Callable<ArrayList<Archive>> {
    CountDownLatch lock;
    private String Date;
    private String DateAdd;
    private Context context;
    String Account;

    public SearchCallableDate(String Date, Context context, String Account, String s) {
        this.Date = Date;
        this.context = context;
        this.Account = Account;
        lock = new CountDownLatch(1);
        DateAdd = s;
    }

    @Override
    public ArrayList<Archive> call() throws Exception {
        final ArrayList<Archive> SearchResult = new ArrayList<>(0);
        OkHttpClient SearchDateClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = builder.setType(MultipartBody.FORM).addFormDataPart("startTime", URLEncoder.encode(Date, "UTF-8"))
                .addFormDataPart("endTime", URLEncoder.encode(DateAdd, "UTF-8"))
                .addFormDataPart("account", URLEncoder.encode(Account, "UTF-8"))
                .addFormDataPart("method", "time").build();
        Request request = new Request.Builder().post(requestBody).url("http://118.25.210.13:8080/Homemory/Search").build();

        SearchDateClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error  :", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BufferedReader bufferreader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                StringBuilder JsonString = new StringBuilder("");
                String temp = "";
                while ((temp = bufferreader.readLine()) != null) {
                    JsonString.append(temp);
                }
                try {
                    Log.e("第一次的请求结果为:", JsonString.toString());
                    Log.e("ACCOUNT", Account);
                    JSONObject jsonObject = new JSONObject(JsonString.toString());
                    int amount = Integer.parseInt(jsonObject.getString("amount"));
                    for (int i = 1; i <= amount; i++) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString(i + ""));
                        String location = URLDecoder.decode(jsonObject1.getString("location"), "UTF-8");
                        String description = URLDecoder.decode(jsonObject1.getString("description"), "UTF-8");
                        String classification = URLDecoder.decode(jsonObject1.getString("classification"), "UTF-8");
                        String visibleRange = URLDecoder.decode(jsonObject1.getString("visibleRange"), "UTF-8");
                        String uploadDate = URLDecoder.decode(jsonObject1.getString("uploadDate"), "UTF-8");
                        String id = URLDecoder.decode(jsonObject1.getString("id"), "UTF-8");
                        String nickName = URLDecoder.decode(jsonObject1.getString("nickName"), "UTF-8");
                        Archive archive = new Archive(location, description, classification, visibleRange, uploadDate,
                                nickName, id);
                        archive.setFileType(jsonObject1.getString("fileType"));
                        byte[] bytes = Base64.decode(jsonObject1.getString("portrait"),android.util.Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        archive.setPortrait(bitmap);
                        SearchResult.add(archive);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lock.countDown();
            }
        });
        lock.await();
        return SearchResult;
    }
}