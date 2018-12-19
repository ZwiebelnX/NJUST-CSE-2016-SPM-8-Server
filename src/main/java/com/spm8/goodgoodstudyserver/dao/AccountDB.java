package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountDB extends CrudRepository <UserEntity,Integer>{
        //使用accountName来查询用户
        @Query("select p from UserEntity p where p.userName=:accountname")
        public List<UserEntity> getByUserName(@Param("accountname") String username);
        //使用Username来查询用户
        public List<UserEntity> getUserEntitiesByUserRealName(@Param("accountname") String username);
}
