package com.example.dragonist.homemory.Bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MemoryBean implements Parcelable{
    private Bitmap portrait; //头像
    private String appellation; //称呼
    private String uploadTime;
    private String type;
    private Bitmap icon;//头像。
    private Bitmap thumbnail; //缩略图,就是展示的图片。
    private String path;
    private String fileName;
    private String relationship;
    private String location;
    private String keyword;
    private String description;
    private String label;
    private String classification;
    private String base64Image;
    private String base64icon;
    public MemoryBean(Bitmap portrait, String appellation, String uploadTime, Bitmap thumbnail) {
        this.portrait = portrait;
        this.appellation = appellation;
        this.uploadTime = uploadTime;
        this.thumbnail = thumbnail;
        path=null;
        type=null;
        fileName=null;
    }

    protected MemoryBean(Parcel in) {
        portrait = in.readParcelable(Bitmap.class.getClassLoader());
        appellation = in.readString();
        uploadTime = in.readString();
        type = in.readString();
        icon = in.readParcelable(Bitmap.class.getClassLoader());
        thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        path = in.readString();
        fileName = in.readString();
        relationship = in.readString();
        location = in.readString();
        keyword = in.readString();
        description = in.readString();
        label = in.readString();
        classification = in.readString();
        base64icon=in.readString();
        base64Image=in.readString();
    }

    public static final Creator<MemoryBean> CREATOR = new Creator<MemoryBean>() {
        @Override
        public MemoryBean createFromParcel(Parcel in) {
            return new MemoryBean(in);
        }

        @Override
        public MemoryBean[] newArray(int size) {
            return new MemoryBean[size];
        }
    };

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

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(portrait, flags);
        dest.writeString(appellation);
        dest.writeString(uploadTime);
        dest.writeString(type);
        dest.writeParcelable(icon, flags);
        dest.writeParcelable(thumbnail, flags);
        dest.writeString(path);
        dest.writeString(fileName);
        dest.writeString(relationship);
        dest.writeString(location);
        dest.writeString(keyword);
        dest.writeString(description);
        dest.writeString(label);
        dest.writeString(classification);
        dest.writeString(base64icon);
        dest.writeString(base64Image);
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getBase64icon() {
        return base64icon;
    }

    public void setBase64icon(String base64icon) {
        this.base64icon = base64icon;
    }

    public MemoryBean(Bitmap portrait, String appellation, String uploadTime, Bitmap icon, Bitmap thumbnail) {
        this.portrait = portrait;
        this.appellation = appellation;
        this.uploadTime = uploadTime;
        this.icon = icon;
        this.thumbnail = thumbnail;
    }

    public MemoryBean(MemoryBean bean) {
        this.appellation = bean.getAppellation();
        this.base64icon = bean.getBase64icon();
        this.base64Image = bean.getBase64Image();
        this.classification = bean.getClassification();
        this.description = bean.getDescription();
        this.fileName = bean.getFileName();
        this.icon = bean.getIcon();
        this.keyword = bean.getKeyword();
        this.label = bean.getLabel();
        this.location = bean.getLocation();
        this.path = bean.getPath();
        this.portrait = bean.getPortrait();
        this.relationship = bean.getRelationship();
        this.thumbnail = bean.getThumbnail();
        this.type = bean.getType();
        this.uploadTime = bean.getUploadTime();
    }
}
