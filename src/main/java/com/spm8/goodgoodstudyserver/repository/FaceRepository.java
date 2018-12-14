package com.spm8.goodgoodstudyserver.repository;

import com.spm8.goodgoodstudyserver.Entities.StudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface FaceRepository extends CrudRepository<StudentEntity,Integer> {
}
