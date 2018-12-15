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
<<<<<<< HEAD
    @Column(name = "CHECK_ID")
=======
    @Column(name = "CHECK_ID", nullable = false)
>>>>>>> origin/master
    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "COURSE_ID")
=======
    @Column(name = "COURSE_ID", nullable = true)
>>>>>>> origin/master
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "ALIVE_PRECENT")
=======
    @Column(name = "ALIVE_PRECENT", nullable = true, length = 255)
>>>>>>> origin/master
    public String getAlivePrecent() {
        return alivePrecent;
    }

    public void setAlivePrecent(String alivePrecent) {
        this.alivePrecent = alivePrecent;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "DESERT_PRECENT")
=======
    @Column(name = "DESERT_PRECENT", nullable = true, length = 255)
>>>>>>> origin/master
    public String getDesertPrecent() {
        return desertPrecent;
    }

    public void setDesertPrecent(String desertPrecent) {
        this.desertPrecent = desertPrecent;
    }

    @Basic
<<<<<<< HEAD
    @Column(name = "CHECK_TIME")
=======
    @Column(name = "CHECK_TIME", nullable = true)
>>>>>>> origin/master
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
