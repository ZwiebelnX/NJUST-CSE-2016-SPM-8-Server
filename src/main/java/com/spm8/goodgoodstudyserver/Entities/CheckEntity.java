package com.spm8.goodgoodstudyserver.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "check", schema = "haohaoxuexi", catalog = "")
public class CheckEntity {
    private int checkId;
    private Integer courseId;
    private String alivePrecent;
    private String desertPrecent;
    private Timestamp checkTime;

    @Id
    @Column(name = "CHECK_ID")
    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    @Basic
    @Column(name = "COURSE_ID")
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "ALIVE_PRECENT")
    public String getAlivePrecent() {
        return alivePrecent;
    }

    public void setAlivePrecent(String alivePrecent) {
        this.alivePrecent = alivePrecent;
    }

    @Basic
    @Column(name = "DESERT_PRECENT")
    public String getDesertPrecent() {
        return desertPrecent;
    }

    public void setDesertPrecent(String desertPrecent) {
        this.desertPrecent = desertPrecent;
    }

    @Basic
    @Column(name = "CHECK_TIME")
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckEntity that = (CheckEntity) o;
        return checkId == that.checkId &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(alivePrecent, that.alivePrecent) &&
                Objects.equals(desertPrecent, that.desertPrecent) &&
                Objects.equals(checkTime, that.checkTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkId, courseId, alivePrecent, desertPrecent, checkTime);
    }
}
