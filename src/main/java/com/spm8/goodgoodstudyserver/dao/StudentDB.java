package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentDB extends CrudRepository<StudentEntity,Integer> {
    //获取参加了一门课的所有学生
    @Query("select p from StudentEntity p,ParticipateEntity o where p.studentId=o.studentId AND o.courseId=:ID")
   List<StudentEntity> getStudentByCourseID(@Param("ID")int courseid);
    //获取全部学生
    @Query("select p from StudentEntity p")
    List<StudentEntity> getALL();

    @Query("select p from StudentEntity p where p.studentId=:ID")
    StudentEntity getStudentByStudentId(@Param("ID")String studentid);

    //获取参加了一门课的第i次签到且签到状态为j的学生
    @Query("select p from StudentEntity p,SignupEntity o where p.studentId=o.studentId AND o.courseId=:ID AND o.signupCnt=:signCNT AND o.signupResult=:result")
    List<StudentEntity> getStudentBySucceessSigned(@Param("ID")int courseid,@Param("signCNT")int signCNT,@Param("result")String result);
}
