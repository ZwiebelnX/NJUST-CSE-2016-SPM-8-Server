package com.spm8.goodgoodstudyserver.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        JSONObject jsonObject = new JSONObject("{ \"hello\" : \"world\" }");
        return jsonObject.toString();
    }
}