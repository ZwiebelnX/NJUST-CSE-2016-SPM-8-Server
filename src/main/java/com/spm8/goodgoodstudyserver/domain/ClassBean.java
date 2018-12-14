package com.spm8.goodgoodstudyserver.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ClassBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

//    @OneToMany(mappedBy ="ClassBean",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<SigninBean> SigninBeans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
