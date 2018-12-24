package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.service.FaceService;
import com.spm8.goodgoodstudyserver.service.StudentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private StudentService studentService; //学生信息的操作（不包括录入）
    private FaceService faceService; //学生信息的录入

    @Autowired
    public StudentController(StudentService studentService, FaceService faceService) {
        this.studentService = studentService;
        this.faceService = faceService;
    }

    //返回所有学生信息
    @RequestMapping(value = "/showAllStudents", produces = "application/json;charset=UTF-8")
    public String showAllStudents(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return studentService.showAllStudents(jsonObject);
    }

    //前端传来学生信息 录入学生信息
    @RequestMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public String updateStudent(MultipartHttpServletRequest request) {
        String studentName = request.getParameter("studentName");
        String studentID = request.getParameter("studentID");
        MultipartFile studentFaceImage = request.getFile("studentFaceImage");
        return faceService.doFaceEnter(studentFaceImage, studentID, studentName);
    }
}
