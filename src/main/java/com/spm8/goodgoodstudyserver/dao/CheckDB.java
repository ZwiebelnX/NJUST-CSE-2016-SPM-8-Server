package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.CheckEntity;
import com.spm8.goodgoodstudyserver.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CheckDB extends CrudRepository<CheckEntity,Integer> {

}
