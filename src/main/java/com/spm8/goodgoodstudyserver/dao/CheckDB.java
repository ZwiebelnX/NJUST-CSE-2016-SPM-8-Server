package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.CheckEntity;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckDB extends CrudRepository<CheckEntity,Integer> {
    @Query("select p from CheckEntity p where p.courseId=:courseID AND p.checkCnt=:CNT")
    List<CheckEntity> getStudentByStudentId(@Param("courseID")int courseID, @Param("CNT")int CNT);
}
