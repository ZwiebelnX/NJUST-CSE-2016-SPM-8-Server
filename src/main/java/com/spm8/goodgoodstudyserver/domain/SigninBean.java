package com.spm8.goodgoodstudyserver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SigninBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userid;
    private int classid;
    private boolean isSign;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isSign() {
        return isSign;
    }

    public int getClassid() {
        return classid;
    }

    public int getUserid() {
        return userid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
