package com.example.dragonist.homemory.Activity.Family;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.Adapter.CategoryAdapter;
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

public class MemberAdapter extends BaseAdapter {
    private OkHttpClient client = new OkHttpClient();
    private Context context;
    private ArrayList<UserInfo> userInfos;
    private boolean hasButton;
    private String account;

    public MemberAdapter(Context context, ArrayList<UserInfo> userInfos) {
        this.context = context;
        this.userInfos = userInfos;
        this.hasButton = false;
    }

    public void setHasButton(boolean hasButton) {
        this.hasButton = hasButton;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_familygroupmember, null);
            viewHolder = new ViewHolder();
            viewHolder.portrait = (ImageView) convertView.findViewById(R.id.ivPortrait);
            viewHolder.nickName = (TextView) convertView.findViewById(R.id.tvNickName);
            viewHolder.confirm = convertView.findViewById(R.id.btnConfirm);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nickName.setText(userInfos.get(position).getNickName());
        viewHolder.portrait.setImageBitmap(userInfos.get(position).getPortrait());
        if (!hasButton) viewHolder.confirm.setVisibility(View.INVISIBLE);
        viewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(account,"UTF-8"))
                            .addFormDataPart("method", URLEncoder.encode("invite", "UTF-8"))
                            .addFormDataPart("receiver",URLEncoder.encode(userInfos.get(position).getNickName(),"UTF-8"))
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

                            }
                        });
                    }
                }).start();
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView nickName;
        public ImageView portrait;
        public Button confirm;
    }

}
