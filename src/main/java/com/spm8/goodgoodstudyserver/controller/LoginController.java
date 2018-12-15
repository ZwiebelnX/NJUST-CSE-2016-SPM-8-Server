package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.service.LoginService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginservice;

    //测试数据库
    @RequestMapping("testdb")
    public String testdb(){
        return loginservice.Test("admin");
    }
    //登入的控制器
    @RequestMapping("login.login")
    @ResponseBody
    public String doLogin(@RequestBody String s){
        String account="",password="";
        try{
            //将字符串转换成jsonObject对象
            JSONObject myJsonObject = new JSONObject(s);
            account=myJsonObject.getString("userName");
            password=myJsonObject.getString("password");
        } catch (JSONException e){
            System.out.println("异常");
        }
        return loginservice.checkAccount(account,password);
    }
}
