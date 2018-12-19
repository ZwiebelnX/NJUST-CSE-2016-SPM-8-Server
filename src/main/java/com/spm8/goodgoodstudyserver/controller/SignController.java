package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.service.SignService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SignController {
    @Autowired
    SignService signService;
    //发起签到的控制器
    @RequestMapping(value = "sign.sign", produces="application/json;charset=UTF-8")//设置返回头为json
    @ResponseBody
    public String doSign(MultipartHttpServletRequest request){
        String courseid="",signType="",imgStr="";
        courseid=request.getParameter("courseID");
        signType=request.getParameter("signType");
        MultipartFile imgfile=request.getFile("img");
        return signService.doSignin(courseid,signType,imgfile);
    }
    @RequestMapping(value = "resign.sign", produces="application/json;charset=UTF-8")//设置返回头为json
    @ResponseBody
    public String doResign(@RequestBody String s) {
        String signCnt="0";
        String courseid="";
        List<StudentEntity>studentlist=new ArrayList<>();
        try{
            JSONObject myJsonObject = new JSONObject(s);
            signCnt=myJsonObject.getString("signCnt");
            courseid=myJsonObject.getString("courseID");
            JSONArray array=myJsonObject.getJSONArray("resignList");
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                StudentEntity temp=new StudentEntity();
                String name=object.getString("studentName");
                String studentid=object.getString("studentID");
                temp.setStudentName(name);
                temp.setStudentId(studentid);
                studentlist.add(temp);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return signService.doResignin(signCnt,courseid,studentlist);
    }
}
