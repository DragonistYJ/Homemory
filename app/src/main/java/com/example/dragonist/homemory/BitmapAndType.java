package com.example.dragonist.homemory;

import android.graphics.Bitmap;

public class BitmapAndType {
    private String filename;
    private String type;
    private String id;
    public BitmapAndType(String url,String type,String id){
        this.filename=url;
        this.type=type;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getfilename() {
        return filename;
    }

    public void setfilename(String url) {
        this.filename= url;
    }
}
