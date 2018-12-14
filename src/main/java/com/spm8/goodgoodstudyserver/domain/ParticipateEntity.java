package com.spm8.goodgoodstudyserver.domain;


import javax.persistence.*;

@Entity
@Table(name="participate")
public class ParticipateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private String courseID;
    @Id
    @Column(name = "STUDENT_ID")
    private String studentID;
}
