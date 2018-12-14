package com.spm8.goodgoodstudyserver.service;

import com.spm8.goodgoodstudyserver.dao.AccountDB;
import com.spm8.goodgoodstudyserver.dao.AccountDBImpl;
import com.spm8.goodgoodstudyserver.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class LoginService {
    @Autowired
    AccountDBImpl accountDB;

    public String checkAccount(String account,String pwd){
        if(account==null||pwd==null)
            return "Input data error";
        UserEntity user=accountDB.getAccountbyUsername(account);
        if(user==null)
            return "No such user";
        String realpwd=new Stringencrypt().DecodeString(user.getPassword());
        if(realpwd.equals(pwd))
            return "Login Success";
        else
            return "Password Error";
    }
    public String Test(String s){
        UserEntity user=accountDB.getAccountbyUsername(s);
        return user.getAccount()+";"+user.getEmail();
    }
}
