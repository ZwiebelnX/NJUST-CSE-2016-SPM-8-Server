package com.spm8.goodgoodstudyserver.domain;

import javax.persistence.*;

@Entity
public class SigninBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

//    @JoinColumn(name="FaceBean")
//    @ManyToOne
//    private FaceBean faceBean;
    /*
    @JoinColumn(name="ClassBean")
    @ManyToOne
    private ClassBean classBean;
    */
//    private int userid;
//    private  int classid;
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

    public void setSign(boolean sign) {
        isSign = sign;
    }

//    public ClassBean getClassBean() {
//        return classBean;
//    }
//
//    public FaceBean getFaceBean() {
//        return faceBean;
//    }
//
//    public void setClassBean(ClassBean classBean) {
//        this.classBean = classBean;
//    }

//    public void setUserid(int userid) {
//        this.userid = userid;
//    }
//    public void setClassid(int classid) {
//        this.classid = classid;
//    }
//
//    public int getUserid() {
//        return userid;
//    }
//
//    public int getClassid() {
//        return classid;
//    }
}
