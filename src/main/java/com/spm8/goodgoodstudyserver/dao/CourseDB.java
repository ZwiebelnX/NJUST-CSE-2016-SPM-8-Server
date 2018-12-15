package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.*;

import java.util.List;

public interface CourseDB {
    List<CourseEntity> getCourseListByTeacherID(int id);
}
