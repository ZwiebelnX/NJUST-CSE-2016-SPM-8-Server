package com.spm8.goodgoodstudyserver.Dao;

import com.spm8.goodgoodstudyserver.Entities.*;

import java.util.List;

public interface CourseDB {
    List<CourseEntity> getCourseListByTeacherID(int id);
}
