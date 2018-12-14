package com.spm8.goodgoodstudyserver.Controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public JSONObject index() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        System.out.println(jsonObject);
        return jsonObject;
    }
}