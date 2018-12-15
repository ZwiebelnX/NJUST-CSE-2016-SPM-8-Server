package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.service.LoginService;
import com.spm8.goodgoodstudyserver.service.Stringencrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Autowired
    Stringencrypt encoder;
    @Resource
    private LoginService loginservice;

    @RequestMapping(value="encode.test")
    public String doencodetest() {
        return encoder.EncodeString("cse2016");
    }
    @RequestMapping("decode.test")
    public String dodecodetest(){
        return encoder.DecodeString("zj471Os");
    }
}
