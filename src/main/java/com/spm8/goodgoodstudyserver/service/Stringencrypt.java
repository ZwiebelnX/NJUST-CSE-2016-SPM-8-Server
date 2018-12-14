package com.spm8.goodgoodstudyserver.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class Stringencrypt {
    static Map<Character,Character> map=new HashMap<Character,Character>();
    static Map<Character,Character> map1=new HashMap<Character,Character>();
    Stringencrypt(){
        String a="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String b="UnzV4baW5GxqQo2eRrjFS6yPdp1O7TmXsuHNE3JfItlKYkw8L0c9MvDhgCBAZi";
        for(int i=0;i<a.length();i++){
            map.put(a.charAt(i),b.charAt(i));
            map1.put(b.charAt(i),a.charAt(i));
        }
    }
    public String EncodeString(String str){
        String s2= str.toLowerCase();
        String d = "";
        for (int i = 0; i < s2.length(); i++) {
            d = d + map.get(s2.charAt(i));
        }
        return d;
    }
    public String DecodeString(String str){
        String d = "";
        for (int i = 0; i < str.length(); i++) {
            d = d + map1.get(str.charAt(i));
        }
        return d;
    }
}
