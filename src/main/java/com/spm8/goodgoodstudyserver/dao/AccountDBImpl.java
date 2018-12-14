package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.domain.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
@Repository
public class AccountDBImpl implements AccountDB {
    @PersistenceContext
    EntityManager entityManager;

    public UserEntity getAccountbyUsername(String accountname){
        Query query=entityManager.createQuery(
                "select p from UserEntity p where p.account=:accountname");
        query.setParameter("accountname",accountname);
        List<UserEntity> resultList =query.getResultList();
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }
}
