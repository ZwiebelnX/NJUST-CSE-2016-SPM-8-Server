package com.spm8.goodgoodstudyserver.service;

import com.spm8.goodgoodstudyserver.dao.AccountDB;
import com.spm8.goodgoodstudyserver.dao.CourseDB;
import com.spm8.goodgoodstudyserver.entities.*;
import com.spm8.goodgoodstudyserver.util.StringEncrypt;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class LoginService {
    private AccountDB accountDB;
    private StringEncrypt encoder;
    private CourseDB courseDB;

    //传入帐号与密码 传出JSON格式字符串以转发信息信息详情见接口文档
    //使用构造器注入
    @Autowired
    public LoginService(AccountDB accountDB, StringEncrypt encoder, CourseDB courseDB) {
        this.accountDB = accountDB;
        this.encoder = encoder;
        this.courseDB = courseDB;
    }

    public String checkAccount(String account, String pwd) {
        String msgString;
        UserEntity user = new UserEntity();
        if (account == null || pwd == null)
            msgString = "INPUT_DATA_ERROR";
            List<UserEntity> userEntityList = accountDB.getByUserName(account);
        if (userEntityList.size() < 1) {
            msgString = "ACCOUNT_ERROR";
        } else {
            user = userEntityList.get(0);
            String realPwd = encoder.DecodeString(user.getPassword());
            if (realPwd.equals(pwd)) {
                msgString = "SUCCESS_TEACHER";
                if (user.getType().equals("ADMIN"))
                    msgString = "SUCCESS_ADMIN";
            } else
                msgString = "PASSWORD_ERROR";
        }

        JSONObject result = new JSONObject();
        result.put("msg", msgString);
        List<CourseEntity> courseList = new LinkedList<>();
        if (msgString.equals("SUCCESS_TEACHER")) {
            result.put("realName", user.getUserRealName());
            result.put("teacherID", user.getUserId());
            result.put("userType", user.getType()); //加入用户类别 以便网页端调用
            courseList = courseDB.getCourseEntitiesByTeacherId(user.getUserId());
        }

        //TODO 网页端管理员登录写入文档
        if (msgString.equals("SUCCESS_ADMIN")) {
            result.put("realName", user.getUserRealName());
            result.put("adminID", user.getUserId());
            result.put("userType", user.getType());
            if(user.getType().equals("ADMIN")){
                Iterable<CourseEntity> tempIterator = courseDB.findAll();
                for(CourseEntity courseEntity : tempIterator){
                    courseList.add(courseEntity);
                }
            }
        }

        JSONArray jsonArray = new JSONArray();
        for (CourseEntity courseEntity : courseList) {
            JSONObject object = new JSONObject();
            object.put("courseID", Integer.toString(courseEntity.getCourseId()));
            object.put("courseName", courseEntity.getCourseName());
            object.put("courseClassroom", courseEntity.getClassroom());
            jsonArray.put(object);
        }
        result.put("courseList", jsonArray);
        return result.toString();
    }
}
