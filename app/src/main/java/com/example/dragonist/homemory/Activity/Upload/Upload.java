package com.example.dragonist.homemory.Activity.Upload;
import com.example.dragonist.*;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.UploadToosPackage.UploadDescription;
import com.example.dragonist.homemory.UploadToosPackage.UploadFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import okio.Buffer;

public class Upload extends AppCompatActivity {
    private Uri Imageuri;
    private String fileSpecies=null;
    public UploadFile uploadFile;
    private UploadDescription uploadDescription;
    private ImageView iv_cancel;
    private ImageView iv_share;
    private ImageView iv_camera;
    private ImageView iv_record;
    private ImageView iv_location;
    private ImageView iv_time_capsule;
    private ImageView iv_deadline;
    private ImageView iv_publish;
    private ImageView iv_image;
    private ImageView iv_video;
    private ImageView iv_music;
    private ImageView iv_document;
    private ImageView ivRemind;
    private ImageView iv_select_class;
    private LinearLayout bigEvent;
    private LinearLayout left_boxs;
    private LinearLayout right_boxs;
    private RadioGroup rgEvent;
    final private int IMAGE=1;
    final private int VIDEO=2;
    final private int MUSIC=3;
    final private int DOCUMENT=4;
    final private int CAMERA=5;
    boolean CHECK_LEFT=false;
    boolean CHECK_RIGHT=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String method = this.getIntent().getExtras().getString("method");
        uploadFile=new UploadFile();
        uploadFile.setBigEvent(false);
        switch (method){
            case "local":
                uploadDescription=new UploadDescription();
                init_local();
                break;
            case "realtime":
                uploadDescription=new UploadDescription();
                init_realtime();
                break;
            case "medio":
                init_local();
                break;
        }
    }

    private void init_local() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_upload_local);
        left_boxs=(LinearLayout)findViewById(R.id.left_box);
        right_boxs=(LinearLayout)findViewById(R.id.right_box);
        iv_time_capsule=(ImageView)findViewById(R.id.timecapsule);
        iv_deadline=(ImageView)findViewById(R.id.deadline);
        left_boxs.setVisibility(View.GONE);
        right_boxs.setVisibility(View.GONE);
        iv_record=(ImageView)findViewById(R.id.record);
        //uploadClassAdapter = new UploadClassAdapter(this);
        iv_cancel = findViewById(R.id.cancel);
        iv_image=(ImageView)findViewById(R.id.image);
        iv_music=(ImageView)findViewById(R.id.music);
        iv_document=(ImageView)findViewById(R.id.document);
        iv_video=(ImageView)findViewById(R.id.video);
        //监听右边的胶囊是否显示
        iv_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CHECK_RIGHT){
                    right_boxs.setVisibility(View.VISIBLE);
                }
                else if(CHECK_RIGHT){
                    right_boxs.setVisibility(View.INVISIBLE);
                }
                CHECK_RIGHT=!CHECK_RIGHT;
            }
        });
        //监听左边时间胶囊是否
        iv_time_capsule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CHECK_LEFT){
                    left_boxs.setVisibility(View.VISIBLE);
                }
                else if(CHECK_LEFT){
                    left_boxs.setVisibility(View.INVISIBLE);
                }
                CHECK_LEFT=!CHECK_LEFT;
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent,IMAGE);
            }
        });
        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("android.intent.action.GET_CONTENT");
                intent.setType("video/*");
                startActivityForResult(intent,VIDEO);
            }
        });
        iv_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("android.intent.action.GET_CONTENT");
                intent.setType("audio/*");
                startActivityForResult(intent,MUSIC);
            }
        });
        iv_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("android.intent.action.GET_CONTENT");
                intent.setType("application/pdf");
                startActivityForResult(intent,DOCUMENT);
            }
        });
        iv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadFile.getFile()!=null){
                    String account = null;
                    account = getSharedPreferences("HOMEMORY",MODE_PRIVATE).getString("account",account);
                    uploadFile.setAccount(account);
                    Intent intent=new Intent( Upload.this,Record.class);
                    intent.putExtra("1",uploadFile);
                    intent.putExtra("2",uploadDescription);
                    intent.putExtra("Type",fileSpecies);
                    startActivity(intent);
                    finish();
                    uploadFile.setDescription(null);
                    uploadFile.setFile(null);
                }
                else{
                    Toast toast= Toast.makeText(getApplicationContext(),"请选择上传的文件",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        ivRemind = findViewById(R.id.ivRemind);
        bigEvent = findViewById(R.id.llBigEvent);
        ivRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bigEvent.getVisibility() == View.INVISIBLE)
                    bigEvent.setVisibility(View.VISIBLE);
                else bigEvent.setVisibility(View.INVISIBLE);
            }
        });

        rgEvent = findViewById(R.id.rgEvent);
        rgEvent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId ==0 ) uploadFile.setBigEvent(true);
                else uploadFile.setBigEvent(false);
                bigEvent.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void init_realtime() {
        setContentView(R.layout.activity_upload_realtime);
        iv_time_capsule=(ImageView)findViewById(R.id.timecapsule);
        iv_deadline=(ImageView)findViewById(R.id.deadline);
        iv_cancel = findViewById(R.id.cancel);
        left_boxs=(LinearLayout)findViewById(R.id.left_box);
        right_boxs=(LinearLayout)findViewById(R.id.right_box);
        iv_camera=(ImageView)findViewById(R.id.camera);
        iv_record=(ImageView)findViewById(R.id.record);
        iv_camera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Imageuri=useCamera();
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Imageuri);
                startActivityForResult(intent,CAMERA);
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CHECK_RIGHT){
                    right_boxs.setVisibility(View.INVISIBLE);
                }
                else if(CHECK_RIGHT){
                    right_boxs.setVisibility(View.VISIBLE);
                }
                CHECK_RIGHT=!CHECK_RIGHT;
            }
        });
        //监听左边时间胶囊是否显示
        iv_time_capsule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!CHECK_LEFT){
                    left_boxs.setVisibility(View.INVISIBLE);
                }
                else if(CHECK_LEFT){
                    left_boxs.setVisibility(View.VISIBLE);
                }
                CHECK_LEFT=!CHECK_LEFT;
            }
        });
        iv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Upload.this,Record.class);
                intent.putExtra("1",uploadFile);
                intent.putExtra("2",uploadDescription);
                intent.putExtra("Type","IMAGE");

                startActivity(intent);
                uploadFile.setDescription(null);
                uploadFile.setFile(null);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected  void onActivityResult(int requestcode, int resultcode , Intent data){
        switch (requestcode){
            case IMAGE:
                Toast.makeText(getApplicationContext(),"已选择图片",Toast.LENGTH_SHORT).show();
                Log.e("32",filePathImage(data));
                uploadFile.setFile(new File(filePathImage(data)));
                uploadFile.setFileName(getName(filePathImage(data)));
                fileSpecies="IMAGE";
                uploadFile.setFileType("Image");
                break;
            case MUSIC:
                Toast.makeText(getApplicationContext(),"已选择音乐",Toast.LENGTH_SHORT).show();
                Log.e("32",filePathMusic(data));
                uploadFile.setFile(new File(filePathMusic(data)));
                uploadFile.setFileName(getName(filePathMusic(data)));
                fileSpecies="MUSIC";
                uploadFile.setFileType("Music");
                break;
            case VIDEO:
                Toast.makeText(getApplicationContext(), "已选择视频", Toast.LENGTH_SHORT).show();
                Log.e("32",filePathVideo(data));
                uploadFile.setFile(new File(filePathVideo(data)));
                uploadFile.setFileName(getName(filePathVideo(data)));
                fileSpecies="VIDEO";
                uploadFile.setFileType("Video");
                break;
            case DOCUMENT:
               /* Toast.makeText(getApplicationContext(),"已选择文件",Toast.LENGTH_SHORT).show();
                Log.e("323",filePathDocument(data));*/
                //System.out.println(getDocumentPath(data));
                uploadFile.setFile(new File(getDocumentPath(data)));
                uploadFile.setFileName(getName(getDocumentPath(data)));
                fileSpecies="DOCUMENT";
                uploadFile.setFileType("Document");
                break;
            case CAMERA:
                writeImageIntoStorage();
                Log.e("111111111111111111",uploadFile.getFile().getPath());
                Toast.makeText(getApplicationContext(), "照片已经拍摄完成", Toast.LENGTH_SHORT).show();
        }
    }
    public  String filePathBeforeImage(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String filePathImage(Intent data){
        Uri uri=data.getData();
        Log.e("qfqf q",uri.getPath());
        String docid= DocumentsContract.getDocumentId(uri);
        String id=docid.split(":")[1];
        String selection= MediaStore.Images.Media._ID+"="+id;
        return filePathBeforeImage(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
    }
    public  String filePathBeforeMusic(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String filePathMusic(Intent data){
        Uri uri=data.getData();
        String docid= DocumentsContract.getDocumentId(uri);
        String id=docid.split(":")[1];
        String selection= MediaStore.Images.Media._ID+"="+id;
        return filePathBeforeImage(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,selection);
    }
    public  String filePathBeforeVideo(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String filePathVideo(Intent data){
        Uri uri=data.getData();
        String docid= DocumentsContract.getDocumentId(uri);
        String id=docid.split(":")[1];
        String selection= MediaStore.Video.Media._ID+"="+id;
        return filePathBeforeImage(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,selection);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getDocumentPath(Intent data){
        String DocumentPath=null;
        Uri uri= data.getData();//获得选择文件的URI
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection = MediaStore.Audio.Media._ID + "=" + id;
                Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        DocumentPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    }
                    cursor.close();
                }
            }
            else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contenUri = ContentUris.withAppendedId(Uri.parse("content://download/public_downloads"), Long.valueOf(docId));
                Cursor cursor = getContentResolver().query(contenUri, null,null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        DocumentPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    }
                    cursor.close();
                }
            }
        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    DocumentPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                }
                cursor.close();
            }
        }
        else if("file".equalsIgnoreCase(uri.getScheme())){
            DocumentPath=uri.getPath();
        }
        return DocumentPath;
    }

    public String getName(String filepath){
        StringTokenizer stringTokenizer=new StringTokenizer(filepath,"/");
        String filename = null;
        while(stringTokenizer.hasMoreTokens()){
            filename=stringTokenizer.nextToken();
        }
        return filename;
    }
    public String getFileType(String filepath){
        StringTokenizer stringTokenizer=new StringTokenizer(filepath,"/");
        String filename = null;
        while(stringTokenizer.hasMoreTokens()){
            filename=stringTokenizer.nextToken();
        }
        String Type=null;
        StringTokenizer stringTokenizer1=new StringTokenizer(filename,".");
        while (stringTokenizer1.hasMoreTokens()){
            Type=stringTokenizer1.nextToken();
        }
        return Type;
    }
    public Uri useCamera(){
        Uri uri;
        File imagefile=new File(getExternalCacheDir(),"outputtest.jpg");
        try {
            if (imagefile.exists()){
                imagefile.delete();
            }
            imagefile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            uri= FileProvider.getUriForFile(Upload.this,"GoldLee",imagefile);
        }
        else {
            uri=Uri.fromFile(imagefile);
        }
        return uri;
    }

    public void writeImageIntoStorage(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        String datestr=simpleDateFormat.format(date);
        try{
            Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(Imageuri));
            File file=new File("/storage/emulated/0/DCIM/Camera/"+datestr+".jpg");
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            uploadFile.setFile(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
