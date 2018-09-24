package com.example.dragonist.homemory.Utils;

import java.util.Date;

public class FamilyMember {
    private int portrait;
    private String appellation;
    private int intimacy;
    private boolean expend;
    private String nickName;
    private String birthday;

    public FamilyMember(int portrait, String appellation, int intimacy, String nickName, String birthday) {
        this(portrait,appellation,intimacy,false,nickName,birthday);
    }

    public FamilyMember(int portrait, String appellation, int intimacy, boolean expend, String nickName, String birthday) {
        this.portrait = portrait;
        this.appellation = appellation;
        this.intimacy = intimacy;
        this.expend = expend;
        this.nickName = nickName;
        this.birthday = birthday;
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
        return expend;
    }

    public void setExpend(boolean expend) {
        this.expend = expend;
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

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }
}
