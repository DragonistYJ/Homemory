package com.example.dragonist.homemory;

import android.graphics.Bitmap;

import java.sql.Timestamp;

public class BitmapAndType {
    private String filename;
    private String type;
    private String id;
    private String relationship;
    private String location;
    private String keyword;
    private String description;
    private String label;
    private String classification;
    private Bitmap icon;
    private String NickName;
    private String uploadTime;
    public BitmapAndType(String url,String type,String id){
        this.filename=url;
        this.type=type;
        this.id=id;
    }

    public BitmapAndType(String filename, String type, String id, String relationship, String location, String keyword, String description, String label, String classification,String Nickname,String upLoadTime) {
        this.filename = filename;
        this.type = type;
        this.id = id;
        this.relationship = relationship;
        this.location = location;
        this.keyword = keyword;
        this.description = description;
        this.label = label;
        this.classification = classification;
        this.NickName=Nickname;
        this.uploadTime=upLoadTime;
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



    public void setfilename(String url) {
        this.filename= url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
