package com.example.dragonist.homemory.Fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Family_Stroll extends Fragment {
    private OkHttpClient client = new OkHttpClient();
    private View view;
    private HorizontalScrollView scrollView;
    private ImageView ivRemind;
    private ImageView ivPicture;
    private ImageView peopleaction;
    private TextView tvBegin;
    private int width;
    private int height;
    private Handler handler;
     private  int temp=1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_family_stroll, container, false);
        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    byte[] result = Base64.decode((String) msg.obj, Base64.DEFAULT);
                    Log.e("1", result.toString());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                    ivPicture.setImageBitmap(bitmap);
                    ivPicture.setVisibility(View.VISIBLE);
                }
            }
        };

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {
        scrollView = view.findViewById(R.id.background);
        ivRemind = view.findViewById(R.id.ivRemind);
        tvBegin = view.findViewById(R.id.tvBegin);
        ivPicture = view.findViewById(R.id.ivPicture);
        peopleaction=view.findViewById(R.id.peoplecation);
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        height = outMetrics.heightPixels;

        tvBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvBegin.getText().equals("开始漫步")) {
                    AnimationDrawable drawable= (AnimationDrawable) peopleaction.getDrawable();
                    drawable.start();
                    tvBegin.setText("停止漫步");
                    float x = new Random().nextFloat() * (width - 25) + 13;
                    float y = new Random().nextFloat() * (height - 25) + 13;
                    ivRemind.setX(x);
                    ivRemind.setY(y);
                    ivRemind.setVisibility(View.VISIBLE);
                } else {
                    tvBegin.setText("开始漫步");
                    ivRemind.setVisibility(View.INVISIBLE);
                }
            }
        });

        ivRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleaction.setVisibility(View.INVISIBLE);
                switch (temp){
                    case 1:
                        ivPicture.setVisibility(View.VISIBLE);
                        ivPicture.setImageResource(R.drawable.shoe);
                        temp=2;
                        break;
                    case 2:
                        ivPicture.setVisibility(View.VISIBLE);
                        ivPicture.setImageResource(R.drawable.redbag);
                        temp=3;
                        break;
                    case 3:
                        ivPicture.setVisibility(View.VISIBLE);
                        ivPicture.setImageResource(R.drawable.birthday);
                        temp=1;
                        break;
                        default:
                            break;
                }

//                if (ivPicture.getVisibility() == View.INVISIBLE) {
//                    MultipartBody.Builder builder = new MultipartBody.Builder();
//                    RequestBody requestBody = null;
//                    try {
//                        requestBody = builder.addFormDataPart("account", URLEncoder.encode(getActivity().getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", ""), "UTF-8"))
//                                .setType(MultipartBody.FORM).build();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    final Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/FamilyStroll").post(requestBody).build();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Call call = client.newCall(request);
//                            call.enqueue(new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//
//                                }
//
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    String result = response.body().string();
//                                    Message msg = new Message();
//                                    msg.what = 1;
//                                    msg.obj = result;
//                                    handler.sendMessage(msg);
//                                }
//                            });
//                        }
//                    }).start();
//                }
            }
        });

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollView.fling(0);
                ivRemind.setX(ivRemind.getX() + (oldScrollX - scrollX));
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivPicture.getVisibility()==View.VISIBLE) {
                    ivPicture.setVisibility(View.INVISIBLE);
                    ivRemind.setVisibility(View.INVISIBLE);
                    tvBegin.setText("开始漫步");
                    peopleaction.setVisibility(View.VISIBLE);
                    peopleaction.setImageResource(R.drawable.action);
                }
            }
        });
    }
}
