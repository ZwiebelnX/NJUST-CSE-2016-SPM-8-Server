package com.spm8.goodgoodstudyserver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FaceBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String Name;
    private String faceToken;
    private String faceImagePath;
//    @OneToMany(mappedBy = "FaceBean",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<SigninBean> SigninBeans;
    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public int getID() {
        return ID;
    }

    public String getFaceImagePath() {
        return faceImagePath;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceImagePath(String faceImagePath) {
        this.faceImagePath = faceImagePath;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }
}
