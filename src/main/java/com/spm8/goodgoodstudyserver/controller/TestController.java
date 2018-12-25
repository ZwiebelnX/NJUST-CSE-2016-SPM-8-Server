package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.dao.AccountDB;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.entities.UserEntity;
import com.spm8.goodgoodstudyserver.service.FaceService;
import com.spm8.goodgoodstudyserver.service.LoginService;
import com.spm8.goodgoodstudyserver.service.SignService;
import com.spm8.goodgoodstudyserver.util.StringEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    private final StringEncrypt encoder;
    private final LoginService loginservice;
    private final StudentDB studentDB;
    private final SignService signService;
    private final AccountDB accountDB;
    private final FaceService faceService;

    @Autowired
    public TestController(StringEncrypt encoder, LoginService loginservice, StudentDB studentDB, SignService signService, AccountDB accountDB,FaceService faceService) {
        this.encoder = encoder;
        this.loginservice = loginservice;
        this.studentDB = studentDB;
        this.signService = signService;
        this.accountDB = accountDB;
        this.faceService=faceService;
    }

    @RequestMapping(value="encode.test")
    public String doencodetest() {
        return encoder.EncodeString("cse2016");
    }
    @RequestMapping("decode.test")
    public String dodecodetest(){
        return encoder.DecodeString("zj471Os");
    }
    //用于测试数据库情况
    @RequestMapping("testdb.test")
    public String Test(){
        List<UserEntity> userlist =accountDB.getByUserName("fishhead");
        UserEntity user;
        if(userlist.size()==0)
            return  "NULL";
        else {
            user=userlist.get(0);
            return user.getUserRealName();
        }
    }
    @RequestMapping("testdb2.test")
    public String dotestdb2(){
        return studentDB.getStudentByCourseID(1).toString();
    }
    @RequestMapping("testsign.test")
    public String dotestsign(){
        return  signService.doSignin("1","SIGN",null);
    }
    @RequestMapping("testresign.test")
    public String doTestResign(){
        List<StudentEntity>list=new ArrayList<StudentEntity>();
        StudentEntity zhao=new StudentEntity();
        zhao.setStudentId("916106840347");
        zhao.setStudentName("赵珉怿");
        list.add(zhao);
        return signService.doResignin("1","1",list);
    }
    @RequestMapping("teststatus.test")
    public String doTestStatus(){
        return faceService.doCheckStatus(null,"1","FIRST_CHECK","0");
    }
}
