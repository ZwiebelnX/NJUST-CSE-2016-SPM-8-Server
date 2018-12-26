package com.spm8.goodgoodstudyserver.service;

import com.mysql.cj.xdevapi.JsonArray;
import com.spm8.goodgoodstudyserver.dao.StudentDB;
import com.spm8.goodgoodstudyserver.entities.StudentEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentDB studentDB;

    @Autowired
    public StudentService(StudentDB studentDB) {
        this.studentDB = studentDB;
    }

    //显示所有的学生
    public String showAllStudents(String userType){
        JSONObject result = new JSONObject();
        //如果是管理员用户 则返回所有学生
        if(userType.equals("ADMIN")){
            List<StudentEntity> studentEntities = studentDB.getALL();
            JSONArray studentArray = new JSONArray();
            if(!studentEntities.isEmpty()){
                for(StudentEntity student : studentEntities){
                    JSONObject tempObject = new JSONObject();
                    tempObject.put("studentName", student.getStudentName());
                    tempObject.put("studentID", student.getStudentId());
                    tempObject.put("studentFaceToken", student.getFaceToken());
                    studentArray.put(tempObject);
                }
                result.put("studentsInfo", studentArray);
                result.put("msg", "ACCESS_SUCCESS");
            }
        }
        //如果是教师用户 则不返回学生
        else{
            result.put("msg", "AUTH_ERROR");
        }
        return result.toString();
    }

    //删除单个学生
    public String deleteStudent(String id){
        //非空时删除
        if(studentDB.getStudentByStudentId(id) != null){
            studentDB.delete(studentDB.getStudentByStudentId(id));
            return new JSONObject().put("msg", "DELETE_SUCCESS").toString();
        }
        else{
            return new JSONObject().put("msg", "CLIENT_DATA_ERROR").toString();
        }
    }
}
