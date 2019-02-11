package com.example.dragonist.homemory.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.dragonist.homemory.Activity.Videoview;

import java.util.jar.Attributes;

public class MyVideoView extends VideoView {
    public MyVideoView(Context context){
        super(context);
    }
    public MyVideoView (Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public MyVideoView(Context context,AttributeSet attributeSet,int defStyleAttr){
        super(context,attributeSet,defStyleAttr);
    }
    protected void onMeasure(int width,int height){
        WindowManager windowManager=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        int thewidth=windowManager.getDefaultDisplay().getWidth();
        //int theheight=windowManager.getDefaultDisplay().getHeight();
        setMeasuredDimension(thewidth,height);
    }
}
