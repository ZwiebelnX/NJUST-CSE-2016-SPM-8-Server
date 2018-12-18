package com.spm8.goodgoodstudyserver.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class StringEncrypt {
    private static Map<Character,Character> encodeMap =new HashMap<Character,Character>();
    private static Map<Character,Character> decodeMap =new HashMap<Character,Character>();
    StringEncrypt(){
        String a="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String b="UnzV4baW5GxqQo2eRrjFS6yPdp1O7TmXsuHNE3JfItlKYkw8L0c9MvDhgCBAZi";
        for(int i=0;i<a.length();i++){
            encodeMap.put(a.charAt(i),b.charAt(i));
            decodeMap.put(b.charAt(i),a.charAt(i));
        }
    }
    public String EncodeString(String str){
        String tempStr= str.toLowerCase();
        String d = "";
        for (int i = 0; i < tempStr.length(); i++) {
            d = d + encodeMap.get(tempStr.charAt(i));
        }
        return d;
    }
    public String DecodeString(String str){
        String d = "";
        for (int i = 0; i < str.length(); i++) {
            d = d + decodeMap.get(str.charAt(i));
        }
        return d;
    }
}
