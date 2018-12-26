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

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

//发起签到的控制器
@RestController
public class SignController {
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @RequestMapping(value = "sign.sign", produces = "application/json;charset=UTF-8")//设置返回头为json
    @ResponseBody
    public String doSign(MultipartHttpServletRequest request) {
        String courseid = "", signType = "", imgStr = "";
        courseid = request.getParameter("courseID");
        signType = request.getParameter("signType");
        MultipartFile imgFile = request.getFile("img");
        return signService.doSignin(courseid, signType, imgFile);
    }

    @RequestMapping(value = "resign.sign", produces = "application/json;charset=UTF-8")//设置返回头为json
    @ResponseBody
    public String doResign(@RequestBody String s) {
        String signCnt = "0";
        String courseid = "";
        List<StudentEntity> studentList = new ArrayList<>();
        try {
            JSONObject myJsonObject = new JSONObject(s);
            signCnt = myJsonObject.getString("signCnt");
            courseid = myJsonObject.getString("courseID");
            JSONArray array = myJsonObject.getJSONArray("resignList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                StudentEntity temp = new StudentEntity();
                String name = object.getString("studentName");
                String studentID = object.getString("studentID");
                temp.setStudentName(name);
                temp.setStudentId(studentID);
                studentList.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return signService.doResignin(signCnt, courseid, studentList);
    }
    @RequestMapping(value = "information.sign", produces = "application/json;charset=UTF-8")//设置返回头为json
    @ResponseBody
    public String doGetInformation(@RequestBody String s){
        int courseID;
        try {
            JSONObject myJsonObject = new JSONObject(s);
            String temp = myJsonObject.getString("courseID");
            courseID = Integer.parseInt(temp);
        }catch (Exception ex){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg","INPUT_DATA_ERROR");
            return  jsonObject.toString();
        }
        return signService.doGetInfo(courseID);
    }
}
