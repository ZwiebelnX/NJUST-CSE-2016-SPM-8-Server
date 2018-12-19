package com.spm8.goodgoodstudyserver.controller;

import com.spm8.goodgoodstudyserver.dao.Impl.StudentDBImpl;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.util.FaceUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/User")
public class FaceRegController {
    @Autowired
    private StudentDB studentDBImpl;
    @RequestMapping(value="/picture")
    public String picture(MultipartFile file, String name) throws IOException {
        if(file == null || "".equals(file.getOriginalFilename())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",  "上传的照片为空");
            System.out.println("上传照片为空");
            return jsonObject.toString();
        }
        String str = FaceUtil.check(file.getBytes());
        System.out.print(str);
        JSONObject json =new JSONObject(str);
        try {
            JSONArray faces = json.getJSONArray("faces");
            if("[]".equals(faces)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg",  "对不起，您上传的不是用户头像或者照片质量不达标，请重新上传！");
                System.out.print("重新上传");
                return jsonObject.toString();
            }
            JSONObject josnToken= faces.getJSONObject(0);
            String faceToken=josnToken.getString("face_token");
            StudentEntity user = new StudentEntity();
            System.out.println("hahaha"+name+" "+faceToken);
            user.setStudentName(name);
            user.setFaceToken(faceToken);
            studentDBImpl.save(user);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",  "系统繁忙，请稍后重试！");
            System.out.print("重试");
            return jsonObject.toString();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",  "上传成功，请登录！");
        System.out.print("成功");
        return jsonObject.toString();
    }
}
