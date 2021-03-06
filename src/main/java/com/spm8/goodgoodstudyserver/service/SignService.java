package com.spm8.goodgoodstudyserver.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.spm8.goodgoodstudyserver.dao.CheckDB;
import com.spm8.goodgoodstudyserver.dao.CourseDB;
import com.spm8.goodgoodstudyserver.dao.SignUpDB;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.CheckEntity;
import com.spm8.goodgoodstudyserver.entities.CourseEntity;
import com.spm8.goodgoodstudyserver.entities.SignupEntity;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Service
public class SignService {
    private final SignUpDB signUpDB;
    private final CourseDB courseDB;
    private final StudentDB studentDB;
    private final FaceService faceService;
    private final CheckDB checkDB;

    @Autowired
    public SignService(CourseDB courseDB, StudentDB studentDB, FaceService faceService, SignUpDB signUpDB,CheckDB checkDB) {
        this.courseDB = courseDB;
        this.studentDB = studentDB;
        this.faceService = faceService;
        this.signUpDB = signUpDB;
        this.checkDB=checkDB;
    }

    //将字符串转换成二进制流
    public byte[] convertStringToByte(String s) {
        if (s == null) return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //具体实现签到
    public String doSignin(String courseID, String signType, MultipartFile img) {
        List<Integer> signedlist = courseDB.getCourseSignCnt(Integer.valueOf(courseID));
        String msg = "";
        JSONObject jsonObject = new JSONObject();
        List<CourseEntity> courseEntityList = courseDB.getCourseEntitiesByCourseId(Integer.valueOf(courseID));
        if (courseEntityList.size() == 0) {
            msg = "SERVER_ERROR_4";
            signType = "ERROR";
        }
        if (signedlist.size() <= 0 || (!signType.equals("SIGN") && !signType.equals("RESIGN"))) {
            msg = "CLIENT_DATA_ERROR";
        } else {
            try {
                int maxCnt = signedlist.get(0);
                List<StudentEntity> studentlist = studentDB.getStudentByCourseID(Integer.valueOf(courseID));
                if (signType.equals("SIGN")) {
                    maxCnt += 1;
                }
                CourseEntity course = courseEntityList.get(0);
                course.setSignupCount(maxCnt);
                courseDB.save(course);
                List<StudentEntity> result = faceService.doSignStudent(studentlist, img);
                Timestamp current_time = new Timestamp(System.currentTimeMillis());
                Map<String, Integer> map = new HashMap<>();
                for (StudentEntity a : studentlist) {
                    map.put(a.getStudentId(), 1);//表示签到了
                }
                for (StudentEntity a : result) {
                    map.put(a.getStudentId(), 2);//表示旷课了
                }
                if (signType.equals("RESIGN")) {//重签
                    List<SignupEntity> signupEntityList = signUpDB.getSignupListBycouseIDAndSignupCNT(Integer.valueOf(courseID), maxCnt);
                    for (SignupEntity a : signupEntityList) {
                        if (map.get(a.getStudentId()) == 1)
                            a.setSignupResult("YES");
                        else
                            a.setSignupResult("NO");
                        signUpDB.save(a);
                    }
                } else {//非重签
                    for (StudentEntity a : studentlist) {
                        SignupEntity tmp = new SignupEntity();
                        if (map.get(a.getStudentId()) == 1)
                            tmp.setSignupResult("YES");
                        else tmp.setSignupResult("NO");
                        tmp.setCourseId(Integer.valueOf(courseID));
                        tmp.setSignupCnt(maxCnt);
                        tmp.setSignupTime(current_time);
                        tmp.setStudentId(a.getStudentId());
                        signUpDB.save(tmp);
                    }
                }
                JSONArray escaspelist = new JSONArray();
                for (StudentEntity a : studentlist) {
                    //System.out.println(a.getStudentName()+"???");
                    JSONObject tmp = new JSONObject();
                    tmp.put("studentName", a.getStudentName());
                    tmp.put("studentID", a.getStudentId());
                    tmp.put("signResult",(map.get(a.getStudentId()) == 1)?"YES":"NO");
                    escaspelist.put(tmp);
                }
                msg = "SUCCESS";
                jsonObject.put("signFailedList", escaspelist);
                jsonObject.put("signCnt", Integer.toString(maxCnt));
            } catch (Exception ex) {
                ex.printStackTrace();
                jsonObject.put("msg", "SERVER_ERROR_6");
                return jsonObject.toString();
            }
        }
        jsonObject.put("msg", msg);
        return jsonObject.toString();
    }

    //具体实现重新签到
    public String doResignin(String signcnt, String courseID, List<StudentEntity> studentlist) {
        String msg = "";
        List<SignupEntity> signuplist = signUpDB.getSignupListBycouseIDAndSignupCNT(Integer.valueOf(courseID), Integer.valueOf(signcnt));
        if (signuplist.size() <= 0)
            msg = "SERVER_ERROR_5";
        else {
            for (SignupEntity sign : signuplist) {
                for (StudentEntity student : studentlist) {
                    if (sign.getStudentId().equals(student.getStudentId())) {
                        sign.setSignupResult("YES");
                        signUpDB.save(sign);
                    }
                }
            }
            msg = "SUCCESS";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        return jsonObject.toString();
    }

    public String doGetInfo(int courseID,String type,int userID){
        int maxCNT;
        try{
            maxCNT=courseDB.getCourseSignCnt(courseID).get(0);
        }catch (Exception ex){
            ex.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg","INPUT_DATA_ERROR");
            return  jsonObject.toString();
        }
        try{
            JSONArray finalarr=new JSONArray();
            for(int i=1;i<=maxCNT;i++){
                JSONObject tmp=new JSONObject();
                tmp.put("CNT",Integer.toString(i));
                tmp.put("signTime",signUpDB.getSignupListBycouseIDAndSignupCNT(Integer.valueOf(courseID),i).get(0).getSignupTime().toString());
                List<StudentEntity>studentEntityList=studentDB.getStudentBySucceessSigned(courseID,i,"NO");
                List<StudentEntity>studentEntityList_acc=studentDB.getStudentBySucceessSigned(courseID,i,"YES");
                List<CheckEntity>checkEntities=checkDB.getStudentByStudentId(courseID,i);
                double cnt=0.0;
                int divisor=checkEntities.size();
                if(divisor>=1){
                    for(CheckEntity temp:checkEntities){
                        double value=Double.parseDouble(temp.getAlivePercent());
                        cnt+=value;
                    }
                    cnt/=divisor;
                    BigDecimal bg = new BigDecimal(cnt);
                    cnt = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    tmp.put("rate",Double.toString(cnt));
                }else{
                    tmp.put("rate","NULL");
                }
                tmp.put("escapeCNT",studentEntityList.size());
                JSONArray jsonArray=new JSONArray();
                for(StudentEntity a:studentEntityList){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("studentName",a.getStudentName());
                    jsonObject.put("studentID",a.getStudentId());
                    jsonObject.put("signResult","NO");
                    jsonArray.put(jsonObject);
                }
                for(StudentEntity a:studentEntityList_acc){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("studentName",a.getStudentName());
                    jsonObject.put("studentID",a.getStudentId());
                    jsonObject.put("signResult","YES");
                    jsonArray.put(jsonObject);
                }
                tmp.put("studentList",jsonArray);
                finalarr.put(tmp);
            }
            JSONObject fin=new JSONObject();
            fin.put("msg","SUCCESS");
            fin.put("signCNT",Integer.toString(maxCNT));
            fin.put("result",finalarr);
            return  fin.toString();
        }catch (Exception ex){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg","SERVER_ERROR");
            return  jsonObject.toString();
        }
    }
}
