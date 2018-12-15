package com.spm8.goodgoodstudyserver.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "signup", schema = "haohaoxuexi", catalog = "")
public class SignupEntity {
    private int signupId;
    private Integer studentId;
    private Integer courseId;
    private Timestamp signupTime;
    private String signupResult;

    @Id
    @Column(name = "SIGNUP_ID", nullable = false)
    public int getSignupId() {
        return signupId;
    }

    public void setSignupId(int signupId) {
        this.signupId = signupId;
    }

    @Basic
    @Column(name = "STUDENT_ID", nullable = true)
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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
    @Column(name = "SIGNUP_TIME", nullable = true)
    public Timestamp getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(Timestamp signupTime) {
        this.signupTime = signupTime;
    }

    @Basic
    @Column(name = "SIGNUP_RESULT", nullable = true, length = 255)
    public String getSignupResult() {
        return signupResult;
    }

    public void setSignupResult(String signupResult) {
        this.signupResult = signupResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupEntity that = (SignupEntity) o;
        return signupId == that.signupId &&
                Objects.equals(studentId, that.studentId) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(signupTime, that.signupTime) &&
                Objects.equals(signupResult, that.signupResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signupId, studentId, courseId, signupTime, signupResult);
    }
}
