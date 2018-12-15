package com.spm8.goodgoodstudyserver.dao.Impl;

import com.spm8.goodgoodstudyserver.dao.AccountDB;
import com.spm8.goodgoodstudyserver.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
@Repository
public class AccountDBImpl implements AccountDB {


    @PersistenceContext
    EntityManager entityManager;

    //以用户名获取账户实体 输入用户名
    public UserEntity getAccountbyUsername(String accountname){
        Query query=entityManager.createQuery(
                "select p from UserEntity p where p.userName=:accountname");
        query.setParameter("accountname",accountname);
        List<UserEntity> resultList =query.getResultList();
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }
}
