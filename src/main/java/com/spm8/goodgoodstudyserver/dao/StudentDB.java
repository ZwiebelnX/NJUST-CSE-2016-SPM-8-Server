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
}
