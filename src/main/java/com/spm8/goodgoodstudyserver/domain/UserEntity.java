package com.spm8.goodgoodstudyserver.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="account")
    private String account;
    @Column(name="password")
    private String password;
    @Column(name="realname")
    private String realname;
    @Column(name="idnumber")
    private String idnumber;
    @Column(name="major")
    private String major;
    @Column(name="college")
    private String college;
    @Column(name="email")
    private String email;
    @Column(name="type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(account, that.account) &&
                Objects.equals(password, that.password) &&
                Objects.equals(realname, that.realname) &&
                Objects.equals(idnumber, that.idnumber) &&
                Objects.equals(major, that.major) &&
                Objects.equals(college, that.college) &&
                Objects.equals(email, that.email)&&
                Objects.equals(type,that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, password, realname, idnumber, major, college, email,type);
    }
}
