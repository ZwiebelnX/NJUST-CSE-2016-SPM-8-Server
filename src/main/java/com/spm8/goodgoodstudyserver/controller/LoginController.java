package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.service.LoginService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private LoginService loginservice;

    //通过构造器进行依赖注入
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginservice = loginService;
    }

    //登入的控制器
    @RequestMapping(value = "login.login", produces = "application/json;charset=UTF-8")//设置返回头为json
    @ResponseBody
    public String doLogin(@RequestBody String s) {
        String account = "", password = "";
        try {
            //将字符串转换成jsonObject对象
            JSONObject myJsonObject = new JSONObject(s);
            account = myJsonObject.getString("userName");
            password = myJsonObject.getString("password");
        } catch (JSONException e) {
            System.out.println("JSON转换异常");
        }
        return loginservice.checkAccount(account, password);
    }
}
