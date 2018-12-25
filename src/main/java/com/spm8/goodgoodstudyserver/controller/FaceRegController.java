package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.dao.Impl.StudentDBImpl;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.service.FaceService;
import com.spm8.goodgoodstudyserver.service.LoginService;
import com.spm8.goodgoodstudyserver.util.FaceUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.IOException;
@Deprecated
@RestController
public class FaceRegController {
    private FaceService faceService;
    @Autowired
    FaceRegController(FaceService faceService) {
        this.faceService=faceService;
    }
    //本段代码已移动至StudentController
//    @RequestMapping(value = "userenter.face",produces="application/json;charset=UTF-8")
//    public String doUserFaceEnter(MultipartHttpServletRequest request) {
//            String name=request.getParameter("uname");
//            String id=request.getParameter("uid");
//            MultipartFile file=request.getFile("uimage");
//            return faceService.doFaceEnter(file,id,name);
//    }
}
