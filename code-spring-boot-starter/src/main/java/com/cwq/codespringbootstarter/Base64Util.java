package com.cwq.codespringbootstarter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
public class Base64Util {
    //加密
    public static String encode(String str){
        String s = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        return s;
    }
    //解密
    public static String decode(String str){
        byte[] bytes = Base64.getDecoder().decode(str);
        String s = new String(bytes);
        return s;
    }
}

