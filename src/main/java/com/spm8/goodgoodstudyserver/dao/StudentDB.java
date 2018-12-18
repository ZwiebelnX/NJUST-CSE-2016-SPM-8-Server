package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDB extends JpaRepository<StudentEntity, Integer> {
}
