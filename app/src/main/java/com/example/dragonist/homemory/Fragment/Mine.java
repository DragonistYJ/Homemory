package com.example.dragonist.homemory.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.Activity.Mine.Setting;
import com.example.dragonist.homemory.R;

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

public class Mine extends Fragment {
    private View view;
    private Handler handler;
    private OkHttpClient client = new OkHttpClient();

    private TextView tvFamilyInformation;
    private TextView tvJoinFamily;
    private TextView tvCreateFamily;
    private ImageView ivAdministrator;
    private TextView tvScore;
    private TextView tvSecedeFamily;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        init();
        check();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        tvFamilyInformation.setVisibility(View.VISIBLE);
                        tvCreateFamily.setVisibility(View.INVISIBLE);
                        tvJoinFamily.setVisibility(View.INVISIBLE);
                        tvSecedeFamily.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tvFamilyInformation.setVisibility(View.VISIBLE);
                        tvCreateFamily.setVisibility(View.INVISIBLE);
                        tvJoinFamily.setVisibility(View.INVISIBLE);
                        tvSecedeFamily.setVisibility(View.VISIBLE);
                        ivAdministrator.setVisibility(View.VISIBLE);
                        tvScore.setVisibility(View.VISIBLE);
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("HOMEMORY",Context.MODE_PRIVATE).edit();
                        editor.putBoolean("Administrator",true);
                        editor.commit();
                        break;
                }
            }
        };
        return view;
    }

    private void init() {
        tvCreateFamily = view.findViewById(R.id.tvCreateFamily);
        tvFamilyInformation = view.findViewById(R.id.tvFamilyInformation);
        tvJoinFamily = view.findViewById(R.id.tvJoinFamily);
        ivAdministrator = view.findViewById(R.id.ivAdministrator);
        tvScore = view.findViewById(R.id.score);
        tvSecedeFamily = view.findViewById(R.id.tvSecedeFamily);
    }

    private void check() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = null;
        try {
            requestBody = builder.addFormDataPart("method", URLEncoder.encode("search", "UTF-8"))
                    .addFormDataPart("account", URLEncoder.encode(getActivity().getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
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
                        String result = response.body().string();
                        result = URLDecoder.decode(result, "UTF-8");
                        String[] results = result.split("-");

                        if (results.length == 1) {
                            if (result.equals("无")) msg.what = 0;
                            else msg.what = 1;
                        } else {
                            msg.what = 2;
                        }

                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
}

