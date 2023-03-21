package org.czw.imagemanger.util;//package com.great.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.great.dto.JsonDto;
//
//import java.util.Map;
//
//public class DtoUtil {
//
//    public static String sucess(int id, String message, String location,Map<String,Object> map){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setLocation(location);
//        dto.setMap(map);
//        return JSONObject.toJSONString(dto);
//
//    }
//    public static String sucess(int id, String message, String location){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setLocation(location);
//        return JSONObject.toJSONString(dto);
//
//    }
//    public static String sucess(int id, String message,Map<String,Object> map ){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setMap(map);
//        return JSONObject.toJSONString(dto);
//    }
//    public static String sucess(int id, String message, String session,String location){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setSession(session);
//        dto.setLocation(location);
//        return JSONObject.toJSONString(dto);
//    }
//    public static String sucess(int id, String message, String session,String token,String location){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setSession(session);
//        dto.setToken(token);
//        dto.setLocation(location);
//        return JSONObject.toJSONString(dto);
//    }
//    public static String sucess(int id, String message, Map<String,Object> map,String token,String location){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setMap(map);
//        dto.setToken(token);
//        dto.setLocation(location);
//        return JSONObject.toJSONString(dto);
//    }
//    public static String sucess(int id, String message){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        return JSONObject.toJSONString(dto);
//    }
//
////    public static String sucess(int id, String message, List<QuestionBank> list){
////        JsonDto dto = new JsonDto();
////        dto.setId(id);
////        dto.setMessage(message);
////        dto.setList(list);
////        return JSONObject.toJSONString(dto);
////    }
//    public static String error(int id, String message,Map<String,Object> map){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        dto.setMap(map);
//        return JSONObject.toJSONString(dto);
//
//    }
//    public static String error(int id, String message){
//        JsonDto dto = new JsonDto();
//        dto.setId(id);
//        dto.setMessage(message);
//        return JSONObject.toJSONString(dto);
//
//    }
//
//    public static String error (String message){
//        JsonDto dto = new JsonDto();
//        dto.setId(4000);
//        dto.setMessage(message);
//        return JSONObject.toJSONString(dto);
//
//    }
//}
