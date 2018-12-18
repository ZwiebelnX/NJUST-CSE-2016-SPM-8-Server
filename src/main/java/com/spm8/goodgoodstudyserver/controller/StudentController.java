package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.service.StudentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    //返回所有学生信息
    @RequestMapping(value = "/showAllStudents", produces="application/json;charset=UTF-8")
    public String showAllStudents(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        return studentService.showAllStudents(jsonObject);
    }

    @RequestMapping(value = "/update", produces="application/json;charset=UTF-8")
    public String updateStudent(@RequestBody String jsonString){
        return jsonString;
    }
}
