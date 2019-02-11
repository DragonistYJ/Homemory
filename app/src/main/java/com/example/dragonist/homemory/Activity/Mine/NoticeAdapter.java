package com.example.dragonist.homemory.Activity.Mine;

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
import com.example.dragonist.homemory.Bean.MessageBean;
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

public class NoticeAdapter extends BaseAdapter {
    private OkHttpClient client = new OkHttpClient();
    private Context context;
    private ArrayList<MessageBean> messages;

    public NoticeAdapter(Context context, ArrayList<MessageBean> messages) {
        this.context = context;
        this.messages = messages;
    }

    public void setMessages(ArrayList<MessageBean> messages) {
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notice, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTime = convertView.findViewById(R.id.time);
            viewHolder.tvValue = convertView.findViewById(R.id.value);
            viewHolder.tvAgree = convertView.findViewById(R.id.agree);
            viewHolder.tvIgnore = convertView.findViewById(R.id.ignore);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTime.setText(messages.get(position).getTimestamp());
        if (messages.get(position).getType().equals("invite")) {
            viewHolder.tvValue.setText(messages.get(position).getSenderName() + "邀请您加入" + messages.get(position).getFamilyName());
        } else if (messages.get(position).getType().equals("apply")) {
            viewHolder.tvValue.setText(messages.get(position).getSenderName() + "申请加入" + messages.get(position).getFamilyName());
        }

        viewHolder.tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                requestBody = builder.addFormDataPart("id", messages.get(position).getId() + "")
                        .addFormDataPart("method","agree")
                        .setType(MultipartBody.FORM).build();
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

                viewHolder.tvAgree.setVisibility(View.INVISIBLE);
                viewHolder.tvIgnore.setVisibility(View.INVISIBLE);
            }
        });

        viewHolder.tvIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                RequestBody requestBody = null;
                try {
                    requestBody = builder.addFormDataPart("id", URLEncoder.encode(messages.get(position).getId() + "","UTF-8"))
                            .addFormDataPart("method","ignore")
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

                viewHolder.tvAgree.setVisibility(View.INVISIBLE);
                viewHolder.tvIgnore.setVisibility(View.INVISIBLE);
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView tvTime;
        public TextView tvValue;
        public TextView tvAgree;
        public TextView tvIgnore;
    }
}
