package com.example.dragonist.homemory.Utils;

import java.util.Date;

public class FamilyMember {
    private String appellation;
    private int intimacy;
    private boolean expend;
    private String nickName;
    private Date birthday;

    public FamilyMember(String appellation, int intimacy, String nickName, Date birthday) {
        this(appellation,intimacy,false,nickName,birthday);
    }

    public FamilyMember(String appellation, int intimacy, boolean expend, String nickName, Date birthday) {
        this.appellation = appellation;
        this.intimacy = intimacy;
        this.expend = expend;
        this.nickName = nickName;
        this.birthday = birthday;
    }
}
