package com.example.dragonist.homemory.Activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.example.dragonist.homemory.R;

import java.io.File;

public class Videoview extends AppCompatActivity {
    private VideoView videoview;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videaoview);
        videoview=(VideoView)findViewById(R.id.video_View);
        String path=getIntent().getStringExtra("path");
        //path="/storage/emulated/0/Tencent/MobileQQ/shortvideo/C4D0F375569B3F9901D16146E1A4511E/8711078246604083401617024961.mp4";
        playVideo(path);
    }
    private void playVideo(String path){
        Log.e("",path);
        videoview.setVideoURI(Uri.fromFile(new File(path)));
        videoview.start();
    }
    protected void onDestory(){
        super.onDestroy();
        if(videoview!=null){
            videoview.suspend();
        }
    }
}

