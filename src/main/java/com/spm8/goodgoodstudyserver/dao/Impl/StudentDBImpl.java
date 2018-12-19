package com.spm8.goodgoodstudyserver.dao.Impl;

import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
@Deprecated
public class StudentDBImpl{
//    StudentDB studentDB;
//    @PersistenceContext
//    EntityManager entityManager;
//
//    @Transactional
//    public void save(StudentEntity studentEntity){
//        studentDB.save(studentEntity);
//    }
//    //@Override
//    public List<StudentEntity> getStudentByCourseID(String courseid) {
//        Query query=entityManager.createQuery(
//                "select p from StudentEntity p,ParticipateEntity o where p.studentId=o.studentId AND p.courseID=:ID");
//        query.setParameter("ID",courseid);
//        List<StudentEntity> list=query.getResultList();
//        return list;
//    }

}
