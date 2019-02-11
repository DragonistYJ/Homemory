package com.example.dragonist.homemory.UploadToosPackage;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadTools {
    public static void postInformation(final UploadFile file, final String host){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                MultipartBody.Builder builder=new MultipartBody.Builder();
                RequestBody requestFile=RequestBody.create(MediaType.parse("application/octet-stream"),file.getFile());
                RequestBody requestBody=null;
                try {
                    requestBody=builder.addFormDataPart("relationship", URLEncoder.encode(file.getDescription().getAboutPeople(),"UTF-8"))
                            .addFormDataPart("location",URLEncoder.encode(file.getDescription().getLocation(),"UTF-8"))
                            .addFormDataPart("keyword",URLEncoder.encode(file.getDescription().getKeyWord(),"UTF-8"))
                            .addFormDataPart("description",URLEncoder.encode(file.getDescription().getDescription(),"UTF-8"))
                            .addFormDataPart("label",URLEncoder.encode(file.getDescription().getLabel(),"UTF-8"))
                            .addFormDataPart("classification",URLEncoder.encode(file.getLabel(),"UTF-8"))
                            .addFormDataPart("fileContext",URLEncoder.encode(file.getFileName(),"UTF-8"),requestFile)
                            .addFormDataPart("visibleRange",URLEncoder.encode(file.getRange(),"UTF-8"))
                            .addFormDataPart("fileType",URLEncoder.encode(file.getFileType(),"UTF-8"))
                            .addFormDataPart("isBigEvent",URLEncoder.encode(String.valueOf(file.isBigEvent()),"UTF-8"))
                            .addFormDataPart("user",URLEncoder.encode(file.getAccount(),"UTF-8"))
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Request request=new Request.Builder().post(requestBody).url(host).build();
                okhttp3.Call call=client.newCall(request);
                try {
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            Log.e("错误信息：",e.toString());
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                         Log.e("反馈信息:",response.toString());

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
