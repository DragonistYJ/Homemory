package com.example.dragonist.homemory.Bean;

import android.graphics.Bitmap;

public class MemoryBean {
    private Bitmap portrait; //头像
    private String appellation; //称呼
    private int uploadTime;
    private String type;
    private Bitmap thumbnail; //缩略图
    private String path;
    private String fileName;
    public MemoryBean(Bitmap portrait, String appellation, int uploadTime, Bitmap thumbnail) {
        this.portrait = portrait;
        this.appellation = appellation;
        this.uploadTime = uploadTime;
        this.thumbnail = thumbnail;
        path=null;
        type=null;
        fileName=null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getPortrait() {
        return portrait;
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public int getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(int uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
