package com.spm8.goodgoodstudyserver.Controller;

import com.spm8.goodgoodstudyserver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginservice;

//    @RequestMapping(value="/hello")
//    public String hello() {
//        return "Hello World!";
//    }
    @RequestMapping("testdb")
    public String testdb(){

        return loginservice.Test("admin");
    }
    @RequestMapping("dologin.login")
    public String doLogin(HttpServletRequest request){
        String account=request.getParameter("account");
        String pwd=request.getParameter("password");
        return loginservice.checkAccount(account,pwd);
    }
}
