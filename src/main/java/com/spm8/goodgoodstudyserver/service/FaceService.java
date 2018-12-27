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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<StudentEntity> doSignStudent(List<StudentEntity> students, MultipartFile file) {
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
            CreatSetUtil.com("stmp");
            for (int j = 0; j < faces.length(); j++) {
                JSONObject josnToken = faces.getJSONObject(j);
                String faceToken = josnToken.getString("face_token");
                AddFaceUtil.add(faceToken, "stmp");
            }
            for (StudentEntity student : students) {
                String dataToken = student.getFaceToken();
                String s = SearchUtil.search(dataToken, "stmp");
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
            DeleteSetUtil.del("stmp");
            return escapeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //输入MAP，遍历MAP 转换成 JSONARRAY
    public JSONArray convertMapintoArray(Map<StudentEntity,String> state){
        JSONArray array=new  JSONArray ();
        for (Map.Entry<StudentEntity, String> entry : state.entrySet()) {
            JSONObject object=new JSONObject();
            StudentEntity key=entry.getKey();
            String value=entry.getValue();
            object.put("studentName",key.getStudentName());
            object.put("studentID",key.getStudentId());
            object.put("checkResult",value);
            array.put(object);
        }
        return  array;
    }
    //检查状态
    public String doCheckStatus(MultipartFile file, String courseID, String type, String checkCNT) {
        String str = "";
        Map<StudentEntity,String>state=new HashMap<>();
        //List<HashMap<StudentEntity,String>>state=new ArrayList<>();//人脸状态，你改一下返回值或者想办法获取一下。
        JSONObject result = new JSONObject();
        List<Integer> checklist = courseDB.getCourseSignCnt(Integer.valueOf(courseID));
        List<StudentEntity>studentEntityList=new ArrayList<>();
        int alive=0;
        if (checklist == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "INPUT_DATA_ERROR_ID");
            return jsonObject.toString();
        }
        int maxCNT;
        try{
            maxCNT = checklist.get(0);
            //获取上节课好好签到的人的个数。
            studentEntityList=studentDB.getStudentBySucceessSigned(Integer.valueOf(courseID),maxCNT,"YES");
        }catch (Exception ex){
            ex.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "SERVER_ERROR");
            return jsonObject.toString();
        }
        if (file == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "INPUT_DATA_ERROR_IMG");
            return jsonObject.toString();
        }
        try {
            str = FaceUtil.check(file.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "CLIENT_DATA_ERROR_IMG");
            return jsonObject.toString();
        }
        try {
            CreatSetUtil.com("tmp");
            JSONObject json = new JSONObject(str);
            JSONArray faces = json.getJSONArray("faces");
            //获取参加课程成功签到的全部学生
            List<StudentEntity> students = studentDB.getStudentBySucceessSigned(Integer.valueOf(courseID),maxCNT,"YES");
            for(StudentEntity student:studentDB.getStudentByCourseID(Integer.valueOf(courseID))){
                state.put(student,"ABSENT");
            }
            for (StudentEntity student : students) {
                String dataToken = student.getFaceToken();
                String id = student.getStudentName();
                boolean flag = true;
                for (int j = 0; j < faces.length(); j++) {
                    JSONObject josnToken = faces.getJSONObject(j);
                    String faceToken = josnToken.getString("face_token");
                    JSONObject attributes=josnToken.getJSONObject("attributes");
                    JSONObject headpose=attributes.getJSONObject("headpose");
                    Float pitch_angle=headpose.getFloat("pitch_angle");
                    String s = PostUtil.compare(dataToken, faceToken);
                    JSONObject comp = new JSONObject(s);
                    float confidence = comp.getFloat("confidence");
                    System.out.println("置信度为" + confidence);
                    JSONObject thresholdsJson = comp.getJSONObject("thresholds");
                    float e5 = thresholdsJson.getFloat("1e-5");
                    System.out.println("误识率为十万分之一的置信度阈值为：" + e5);
                    if ((double) confidence >= (double) e5) {
                        System.out.println( id + "抬头角度为"+pitch_angle);
                        //HashMap<StudentEntity, String> map = new HashMap<>();
                        //map.put(student,pitch_angle.toString());//返回的是学生+抬头角度[-180~180],String变量；
                        if(pitch_angle<=-30.0||pitch_angle>=30.0)state.put(student,"DOWN");
                        else{
                            state.put(student,"ALIVE");
                            alive++;
                        }
                        flag = false;
                        break;
                    }
                    if(flag==false)break;
                }
                if (flag) {
                    state.put(student,"SLEEP");
                    System.out.println("学生"+student.getStudentName()+"睡觉");
                }
            }
            int allStudent =studentEntityList.size();
            alive=(alive>allStudent)?allStudent:alive;
            double percent_temp = 1.0*alive / allStudent;
            BigDecimal bg = new BigDecimal(percent_temp);
            double percent = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            CheckEntity checkEntity = new CheckEntity();
            checkEntity.setAlivePercent(Double.toString(percent));
            checkEntity.setCourseId(Integer.parseInt(courseID));
            Timestamp current_time = new Timestamp(System.currentTimeMillis());
            checkEntity.setCheckTime(current_time);
            result.put("result", Double.toString(percent));
            checkEntity.setCheckCnt(maxCNT);
            if (type.equals("FIRST_CHECK")) {
                checkDB.save(checkEntity);
                String temp = Integer.toString(maxCNT);
                result.put("checkCNT", temp);
                List<CourseEntity> list = courseDB.getCourseEntitiesByCourseId(Integer.valueOf(courseID));
                CourseEntity temp_2 = list.get(0);
                temp_2.setCheckCount(maxCNT);
                courseDB.save(temp_2);
                result.put("msg", "SUCCESS");
                result.put("studentList",convertMapintoArray(state));
                return result.toString();
            } else if (type.equals("RECHECK")) {

                checkDB.save(checkEntity);
                result.put("checkCNT", Integer.toString(maxCNT));
                result.put("msg", "SUCCESS");
                result.put("studentList",convertMapintoArray(state));
                return result.toString();
            } else {
                result.put("msg", "INPUT_DATA_ERROR");
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
