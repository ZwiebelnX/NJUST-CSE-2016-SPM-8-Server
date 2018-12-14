package com.spm8.goodgoodstudyserver.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "haohaoxuexi", catalog = "")
public class StudentEntity {
    private int studentId;
    private String studentName;
    private String faceToken;

    @Id
    @Column(name = "STUDENT_ID")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "STUDENT_NAME")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Basic
    @Column(name = "FACE_TOKEN")
    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return studentId == that.studentId &&
                Objects.equals(studentName, that.studentName) &&
                Objects.equals(faceToken, that.faceToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, faceToken);
    }
}