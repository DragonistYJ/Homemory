package com.example.dragonist.homemory.Utils;

import android.graphics.Bitmap;

import java.util.Date;

public class FamilyMember {
    private Bitmap portrait;
    private String appellation;
    private int intimacy;
    private boolean isExpend;
    private String nickName;
    private String birthday;
    private boolean isAdministrator;

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

    public int getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public boolean isExpend() {
        return isExpend;
    }

    public void setExpend(boolean expend) {
        isExpend = expend;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public FamilyMember(Bitmap portrait, boolean isExpend, String nickName, String birthday, boolean isAdministrator) {
        this.portrait = portrait;
        this.isExpend = isExpend;
        this.nickName = nickName;
        this.birthday = birthday;
        this.isAdministrator = isAdministrator;
    }
}
