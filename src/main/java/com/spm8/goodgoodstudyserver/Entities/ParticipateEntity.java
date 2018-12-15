package com.spm8.goodgoodstudyserver.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "participate", schema = "haohaoxuexi", catalog = "")
@IdClass(ParticipateEntityPK.class)
public class ParticipateEntity {
    private int courseId;
    private int studentId;

    @Id
    @Column(name = "COURSE_ID", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "STUDENT_ID", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipateEntity that = (ParticipateEntity) o;
        return courseId == that.courseId &&
                studentId == that.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, studentId);
    }
}
