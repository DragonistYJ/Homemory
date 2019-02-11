package com.example.dragonist.homemory.Bean;

import android.graphics.Bitmap;

public class UserInfo {
    private String account;
    private String password;
    private String nickName;
    private String sex;
    private String year;
    private String month;
    private String day;
    private String birthday;
    private String age;
    private String homeTown;
    private String location;
    private String family;
    private Bitmap portrait;

    public UserInfo(String account, String password, String nickName, String sex, String year, String month, String day, String age, String homeTown, String location, String family) {
        this.account = account;
        this.password = password;
        this.nickName = nickName;
        this.sex = sex;
        this.year = year;
        this.month = month;
        this.day = day;
        this.age = age;
        this.homeTown = homeTown;
        this.location = location;
        this.family = family;
    }

    public UserInfo(String nickName, Bitmap portrait) {
        this.nickName = nickName;
        this.portrait = portrait;
    }

    public UserInfo(String nickName, String birthday, Bitmap portrait) {
        this.nickName = nickName;
        this.birthday = birthday;
        this.portrait = portrait;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Bitmap getPortrait() {
        return portrait;
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
    }
}
