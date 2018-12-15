package com.spm8.goodgoodstudyserver.entities;

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
    private Integer checkCnt;

    @Id
    @Column(name = "CHECK_ID", nullable = false)
    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    @Basic
    @Column(name = "COURSE_ID", nullable = true)
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "ALIVE_PRECENT", nullable = true, length = 255)
    public String getAlivePrecent() {
        return alivePrecent;
    }

    public void setAlivePrecent(String alivePrecent) {
        this.alivePrecent = alivePrecent;
    }

    @Basic
    @Column(name = "DESERT_PRECENT", nullable = true, length = 255)
    public String getDesertPrecent() {
        return desertPrecent;
    }

    public void setDesertPrecent(String desertPrecent) {
        this.desertPrecent = desertPrecent;
    }

    @Basic
    @Column(name = "CHECK_TIME", nullable = true)
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "CHECK_CNT", nullable = true)
    public Integer getCheckCnt() {
        return checkCnt;
    }

    public void setCheckCnt(Integer checkCnt) {
        this.checkCnt = checkCnt;
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
                Objects.equals(checkTime, that.checkTime) &&
                Objects.equals(checkCnt, that.checkCnt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkId, courseId, alivePrecent, desertPrecent, checkTime, checkCnt);
    }
}