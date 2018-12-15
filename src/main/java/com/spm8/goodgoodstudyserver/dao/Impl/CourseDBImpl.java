package com.spm8.goodgoodstudyserver.dao.Impl;

import com.spm8.goodgoodstudyserver.dao.CourseDB;
import com.spm8.goodgoodstudyserver.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CourseDBImpl implements CourseDB {
    @PersistenceContext
    EntityManager entityManager;
    //获取一名教师的所有课程 输入id 返回课程List
    public List<CourseEntity> getCourseListByTeacherID(int id){
        List<CourseEntity>list=new ArrayList<>();
        Query query=entityManager.createQuery(
                "select p from CourseEntity p where p.teacherId=:ID");
        query.setParameter("ID",id);
        list =query.getResultList();
        return list;
    }
}
