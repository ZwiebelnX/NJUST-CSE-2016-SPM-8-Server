package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.SignupEntity;
import com.spm8.goodgoodstudyserver.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SignUpDB extends CrudRepository<SignupEntity,Integer> {
    //使用courseid和signcnt来查询已有的签到记录
    @Query("select p from SignupEntity p where p.courseId=:courseID AND p.signupCnt=:signupCNT")
    List<SignupEntity> getSignupListBycouseIDAndSignupCNT(@Param("courseID") String courseID,@Param("signupCNT") String signupCNT);
}
