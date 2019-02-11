package com.example.dragonist.homemory.Bean;

import java.sql.Timestamp;

public class MessageBean {
    private String type;
    private String senderName;
    private String receiverName;
    private String familyName;
    private String timestamp;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public MessageBean(String type, String senderName, String receiverName, String familyName, String timestamp, int id) {
        this.type = type;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.familyName = familyName;
        this.timestamp = timestamp;
        this.id = id;
    }
}
