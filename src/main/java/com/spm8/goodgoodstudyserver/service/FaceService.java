package com.spm8.goodgoodstudyserver.service;

import com.spm8.goodgoodstudyserver.dao.AccountDB;
import com.spm8.goodgoodstudyserver.dao.CheckDB;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.CheckEntity;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import com.spm8.goodgoodstudyserver.util.AddFaceUtil;
import com.spm8.goodgoodstudyserver.util.FaceUtil;
import com.spm8.goodgoodstudyserver.util.PostUtil;
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
    private StudentDB studentDB;
    private CheckDB checkDB;
    @Autowired
    FaceService(StudentDB studentDB,CheckDB checkDB){
        this.studentDB=studentDB;
        this.checkDB=checkDB;
    }
    //录入人脸
    public String doFaceEnter(MultipartFile file, String id, String name){
        if(file == null || "".equals(file.getOriginalFilename())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",  "INPUTDATA_ERROR");
            System.out.println("上传照片为空");
            return jsonObject.toString();
        }
        try {
            String str = FaceUtil.check(file.getBytes());
            System.out.print(str);
            JSONObject json =new JSONObject(str);
            JSONArray faces = json.getJSONArray("faces");
            if("[]".equals(faces)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg",  "INPUTDATA_BAD");//输入照片质量过差
                System.out.print("重新上传");
                return jsonObject.toString();
            }
            JSONObject josnToken= faces.getJSONObject(0);
            String faceToken=josnToken.getString("face_token");
            StudentEntity user = new StudentEntity();
            System.out.println("hahaha"+name+" "+faceToken);
            user.setStudentName(name);
            user.setStudentId(id);
            user.setFaceToken(faceToken);
            studentDB.save(user);
            List<StudentEntity> students = studentDB.getALL();
            int count=students.size()-1;
            AddFaceUtil.add(faceToken,name);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",  "SERVER_ERROR");//系统繁忙
            System.out.print("重试");
            return jsonObject.toString();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",  "SUCCESS");//成功
        System.out.print("成功");
        return jsonObject.toString();
    }
    //处理统计事物
    public List<StudentEntity>doSignStudent(List<StudentEntity>students,MultipartFile file)throws Exception{
        List<StudentEntity>escapeList=new ArrayList<>();
        String str = FaceUtil.check(file.getBytes());
        JSONObject json =new JSONObject(str);
        try {
            JSONArray faces = json.getJSONArray("faces");
            System.out.print("总人数为："+faces.length());
            for(int i=0;i<students.size();i++){
                String datatoken=students.get(i).getFaceToken();
                String id=students.get(i).getStudentId();
                boolean flag=true;
                for(int j=0;j<faces.length();j++) {
                    JSONObject josnToken = faces.getJSONObject(j);
                    String faceToken = josnToken.getString("face_token");
                    String s= PostUtil.compare(datatoken,faceToken);
                    JSONObject comp= new JSONObject(s);
                    Float confidence = comp.getFloat("confidence");
                    System.out.println("置信度为" + confidence);
                    JSONObject thresholdsJson = comp.getJSONObject("thresholds");
                    Float e5 = thresholdsJson.getFloat("1e-5");
                    System.out.println("误识率为十万分之一的置信度阈值为：" + e5);
                    if (Double.valueOf(confidence) >= Double.valueOf(e5)) {
                        //更新数据库的签到数据以及到课学生数量更新+1
                        System.out.println("第"+id+"人极度相似，登录成功！");
                        flag=false;
                        break;
                    } else {
                        continue;
                    }

                }
                if(flag){
                    escapeList.add(students.get(i));
                }
            }
            //总到课人数加入数据库
            return escapeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //检查状态
    public String doCheckStatus(MultipartFile file,String courseID,String type,String checkCNT){
        try {
            String str = FaceUtil.check(file.getBytes());
            JSONObject json =new JSONObject(str);
            JSONArray faces = json.getJSONArray("faces");
            System.out.print("总人数为："+faces.length());
            //获取上课的总人数，目前能监测到的人脸数量为faces.length(),减一下，不好好听课的同学数量返回到数据库。
            int allStudent=studentDB.getStudentByCourseID(Integer.valueOf(courseID)).size();
            int escaped=allStudent-faces.length();
            double percent=escaped*1.0/allStudent;
            CheckEntity checkEntity=new CheckEntity();
            checkEntity.setAlivePercent(Double.toString(percent));
            checkEntity.setCourseId(Integer.parseInt(courseID));
            Timestamp current_time= new Timestamp(System.currentTimeMillis());
            checkEntity.setCheckTime(current_time);
            json.put("result",Double.toString(percent));
            if(type.equals("FIRST_CHECK")){
                checkEntity.setCheckCnt(1);
                checkDB.save(checkEntity);
                json.put("checkCNT","1");
            }else if(type.equals("RECHECK ")){
                checkEntity.setCheckCnt(Integer.parseInt(checkCNT));
                checkDB.save(checkEntity);
                json.put("checkCNT",checkCNT);
            }else{
                json.put("msg","INPUTDATA_ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",  "SERVER_ERROR");
            return jsonObject.toString();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",  "SUCCESS");
        System.out.print("成功");
        return jsonObject.toString();
    }
}
