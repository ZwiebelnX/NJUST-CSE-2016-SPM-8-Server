package com.spm8.goodgoodstudyserver.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.spm8.goodgoodstudyserver.dao.AccountDB;
import com.spm8.goodgoodstudyserver.dao.CheckDB;
import com.spm8.goodgoodstudyserver.dao.CourseDB;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.CheckEntity;
import com.spm8.goodgoodstudyserver.entities.CourseEntity;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaceService {
    final private StudentDB studentDB;
    final private CheckDB checkDB;
    final private CourseDB courseDB;

    @Autowired
    FaceService(StudentDB studentDB, CheckDB checkDB, CourseDB courseDB) {
        this.studentDB = studentDB;
        this.checkDB = checkDB;
        this.courseDB = courseDB;
    }

    //录入人脸
    public String doFaceEnter(MultipartFile file, String id, String name) {
        if (file == null || "".equals(file.getOriginalFilename())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "INPUT_DATA_ERROR");
            System.out.println("上传照片为空");
            return jsonObject.toString();
        }
        try {
            String str = FaceUtil.check(file.getBytes());
            System.out.print(str);
            JSONObject json = new JSONObject(str);
            JSONArray faces = json.getJSONArray("faces");
            if ("[]".equals(faces)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", "INPUT_DATA_BAD");//输入照片质量过差
                System.out.print("重新上传");
                return jsonObject.toString();
            }
            JSONObject josnToken = faces.getJSONObject(0);
            String faceToken = josnToken.getString("face_token");
            StudentEntity user = new StudentEntity();
            System.out.println("hahaha" + name + " " + faceToken);
            user.setStudentName(name);
            user.setStudentId(id);
            user.setFaceToken(faceToken);
            studentDB.save(user);
            List<StudentEntity> students = studentDB.getALL();
            int count = students.size() - 1;
            AddFaceUtil.add(faceToken, id);
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "SERVER_ERROR_1");//系统繁忙
            System.out.print("重试");
            return jsonObject.toString();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "SUCCESS");//成功
        System.out.print("成功");
        return jsonObject.toString();
    }

    //处理统计事物
    public List<StudentEntity> doSignStudent(List<StudentEntity> students, MultipartFile file, String stmp) {
        List<StudentEntity> escapeList = new ArrayList<>();
        try {
            String str = FaceUtil.check(file.getBytes());
            JSONObject json = new JSONObject(str);
            JSONArray faces = json.getJSONArray("faces");
            System.out.print("总人数为：" + faces.length());
//            for (StudentEntity student : students) {
//                String dataToken = student.getFaceToken();
//                String id = student.getStudentName();
//                boolean flag = true;
//                for (int j = 0; j < faces.length(); j++) {
//                    JSONObject josnToken = faces.getJSONObject(j);
//                    String faceToken = josnToken.getString("face_token");
//                    String s = PostUtil.compare(dataToken, faceToken);
//                    JSONObject comp = new JSONObject(s);
//                    float confidence = comp.getFloat("confidence");
//                    System.out.println("置信度为" + confidence);
//                    JSONObject thresholdsJson = comp.getJSONObject("thresholds");
//                    float e5 = thresholdsJson.getFloat("1e-5");
//                    System.out.println("误识率为十万分之一的置信度阈值为：" + e5);
//                    if ((double) confidence >= (double) e5) {
//                        //更新数据库的签到数据以及到课学生数量更新+1
//                        System.out.println( id + "极度相似，登录成功！");
//                        flag = false;
//                        break;
//                    }
//                    if(flag==false)break;
//                }
//
//                if (flag) {
//                    escapeList.add(student);
//                    System.out.println("学生"+student.getStudentName()+"跷课");
//                }
//            }
            CreatSetUtil.com(stmp);
            for (int j = 0; j < faces.length(); j++) {
                JSONObject josnToken = faces.getJSONObject(j);
                String faceToken = josnToken.getString("face_token");
                AddFaceUtil.add(faceToken, stmp);
            }
            for (StudentEntity student : students) {
                String dataToken = student.getFaceToken();
                String s = SearchUtil.search(dataToken, stmp);
                JSONObject comp = new JSONObject(s);
                JSONArray results = comp.getJSONArray("results");
                JSONObject object = results.getJSONObject(0);
                float confidence = object.getFloat("confidence");
                System.out.println("置信度为" + confidence);
                JSONObject thresholdsJson = comp.getJSONObject("thresholds");
                float e5 = thresholdsJson.getFloat("1e-5");
                System.out.println("误识率为十万分之一的置信度阈值为：" + e5);
                if ((double) confidence >= (double) e5) {
                    System.out.println(student.getStudentName() + "极度相似，登录成功！");
                } else escapeList.add(student);
            }
            //总到课人数加入数据库
            return escapeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //检查状态
    public String doCheckStatus(MultipartFile file, String courseID, String type, String checkCNT) {
        String str = "";
        JSONObject result = new JSONObject();
        List<Integer> checklist = courseDB.getCourseCheckCnt(Integer.valueOf(courseID));
        if (checklist == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "SERVER_ERROR_2");
            return jsonObject.toString();
        }
        int maxCNT = checklist.get(0);
        if (file == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "CLIENT_DATA_ERROR");
            return jsonObject.toString();
        }
        try {
            str = FaceUtil.check(file.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "CLIENT_DATA_ERROR");
            return jsonObject.toString();
        }
        try {
            JSONObject json = new JSONObject(str);
            JSONArray faces = json.getJSONArray("faces");
            System.out.print("总人数为：" + faces.length());
            //获取上课的总人数，目前能监测到的人脸数量为faces.length(),减一下，不好好听课的同学数量返回到数据库。
            int allStudent = studentDB.getStudentByCourseID(Integer.valueOf(courseID)).size();
            int escaped = allStudent - faces.length();
            double percent = escaped * 100.0 / allStudent;
            CheckEntity checkEntity = new CheckEntity();
            checkEntity.setAlivePercent(Double.toString(percent));
            checkEntity.setCourseId(Integer.parseInt(courseID));
            Timestamp current_time = new Timestamp(System.currentTimeMillis());
            checkEntity.setCheckTime(current_time);
            result.put("result", Double.toString(percent));
            if (type.equals("FIRST_CHECK")) {
                checkEntity.setCheckCnt(maxCNT + 1);
                checkDB.save(checkEntity);
                String temp = Integer.toString(maxCNT + 1);
                result.put("checkCNT", temp);
                List<CourseEntity> list = courseDB.getCourseEntitiesByCourseId(Integer.valueOf(courseID));
                CourseEntity temp_2 = list.get(0);
                temp_2.setCheckCount(maxCNT + 1);
                courseDB.save(temp_2);
                result.put("msg", "SUCCESS");
                return result.toString();
            } else if (type.equals("RECHECK")) {
                checkEntity.setCheckCnt(Integer.parseInt(checkCNT));
                checkDB.save(checkEntity);
                result.put("checkCNT", checkCNT);
                result.put("msg", "SUCCESS");
                return result.toString();
            } else {
                result.put("msg", "CLIENT_DATA_ERROR");
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "SERVER_ERROR_3");
            return jsonObject.toString();
        }
    }
}
