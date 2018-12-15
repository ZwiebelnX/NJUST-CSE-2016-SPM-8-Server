package com.spm8.goodgoodstudyserver.service;

import com.spm8.goodgoodstudyserver.dao.Impl.AccountDBImpl;
import com.spm8.goodgoodstudyserver.dao.CourseDB;
import com.spm8.goodgoodstudyserver.entities.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    AccountDBImpl accountDB;
    @Autowired
    Stringencrypt encoder;
    @Autowired
    CourseDB courseDB;
    //传入帐号与密码 传出JSON格式字符串以转发信息信息详情见接口文档
    public String checkAccount(String account,String pwd){
        String tmp;
        if(account==null||pwd==null)
            tmp= "INPUT_DATA_ERROR";
        UserEntity user=accountDB.getAccountbyUsername(account);
        if(user==null) {
            tmp = "ACCOUNT_ERROR";
        }
        else {
            String realPwd = encoder.DecodeString(user.getPassword());
            if (realPwd.equals(pwd)) {
                tmp = "SUCCESS_TEACHER";
                if(user.getType().equals("ADMIN"))
                    tmp="SUCCESS_ADMIN";
            }
            else
                tmp = "PASSWORD_ERROR";
        }
        JSONObject result=new JSONObject();
        result.put("msg",tmp);
        if(tmp.equals("SUCCESS_TEACHER")){
            result.put("realName",user.getUserRealName());
            result.put("teacherID",user.getUserId());
            List<CourseEntity>list=courseDB.getCourseListByTeacherID(user.getUserId());
            JSONArray jsonArray=new JSONArray();
            for(CourseEntity courseEntity :list){
                JSONObject object=new JSONObject();
                object.put("courseID",courseEntity.getCourseId());
                object.put("courseName",courseEntity.getCourseName());
                object.put("courseClassroom",courseEntity.getClassroom());
                jsonArray.put(object);
            }
            result.put("courseList",jsonArray);
        }
        String jsonString=result.toString();
        System.out.println(jsonString);
        return jsonString;
    }
    //用于测试数据库情况
    public String Test(String s){
        UserEntity user=accountDB.getAccountbyUsername(s);
        return user.getUserName()+";"+user.getCollege();
    }
}
