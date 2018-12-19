package com.spm8.goodgoodstudyserver.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "haohaoxuexi", catalog = "")
public class StudentEntity {
    private String studentId;
    private String studentName;
    private String faceToken;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "STUDENT_ID", nullable = false, length = 255)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "STUDENT_NAME", nullable = true, length = 255)
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Basic
    @Column(name = "FACE_TOKEN", nullable = true, length = 255)
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
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(studentName, that.studentName) &&
                Objects.equals(faceToken, that.faceToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, faceToken);
    }
}
