package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDB extends CrudRepository<CourseEntity,Integer> {
    //返回课程一门课程的签到次数
    @Query("select p.signupCount FROM CourseEntity p where p.courseId=:id")
    List<Integer> getCourseSignCnt(@Param("id") int courseid);
    //返回一门课程的检查次数
    @Query("select p.checkCount FROM CourseEntity p where p.courseId=:id")
    List<Integer> getCourseCheckCnt(@Param("id") int courseid);
    //返回一个教师的所有课程
    @Query("select p from CourseEntity p where p.teacherId=:id")
    List<CourseEntity>getCourseEntitiesByTeacherId(@Param("id") int teacherid);
    @Query("select p from CourseEntity p where p.courseId=:id")
    List<CourseEntity>getCourseEntitiesByCourseId(@Param("id") int courseid);
}
