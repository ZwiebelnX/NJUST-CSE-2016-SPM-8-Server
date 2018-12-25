package com.spm8.goodgoodstudyserver.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "haohaoxuexi", catalog = "")
public class CourseEntity {
    private int courseId;
    private String courseName;
    private Integer teacherId;
    private String credit;
    private String classroom;
    private String classhour;
    private int signupCount;
    private int checkCount;
    @Id
    @Column(name = "COURSE_ID", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "COURSE_NAME", nullable = true, length = 255)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "TEACHER_ID", nullable = true)
    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Basic
    @Column(name = "CREDIT", nullable = true, length = 255)
    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Basic
    @Column(name = "CLASSROOM", nullable = true, length = 255)
    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    @Basic
    @Column(name = "CLASSHOUR", nullable = true, length = 255)
    public String getClasshour() {
        return classhour;
    }

    public void setClasshour(String classhour) {
        this.classhour = classhour;
    }

    @Basic
    @Column(name = "SIGNUP_COUNT", nullable = true, length = 255)
    public int getSignupCount() {
        return signupCount;
    }

    public void setSignupCount(int signupCount) {
        this.signupCount = signupCount;
    }

    @Basic
    @Column(name = "CHECK_COUNT", nullable = true, length = 255)
    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return courseId == that.courseId &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(teacherId, that.teacherId) &&
                Objects.equals(credit, that.credit) &&
                Objects.equals(classroom, that.classroom) &&
                Objects.equals(classhour, that.classhour) &&
                Objects.equals(signupCount, that.signupCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, teacherId, credit, classroom, classhour, signupCount);
    }

}
