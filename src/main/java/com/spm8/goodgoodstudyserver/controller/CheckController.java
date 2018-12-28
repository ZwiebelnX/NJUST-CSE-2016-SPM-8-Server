package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class CheckController {
    private final FaceService faceService;

    @Autowired
    CheckController(FaceService faceService) {
        this.faceService = faceService;
    }

    //检查学生上课状态
    @RequestMapping(value = "status.check", produces = "application/json;charset=UTF-8")
    public String doCheck(MultipartHttpServletRequest request) {
        String courseID = request.getParameter("courseID");
        String type = request.getParameter("type");
        MultipartFile file = request.getFile("img");
        String checkCNT = request.getParameter("checkCNT");
        return faceService.doCheckStatus(file, courseID, type);
    }


}
