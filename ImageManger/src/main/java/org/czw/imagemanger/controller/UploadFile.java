package org.czw.imagemanger.controller;

import com.alibaba.fastjson.JSONObject;

import org.czw.imagemanger.dto.JsonDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/putfile")
public class UploadFile {
    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile file) {
        JsonDto<Object> jsonDto = new JsonDto<>();
        try {
//            System.out.println("uuuuuuuu");
            String path = "G:/exam/image/";
//            String path = "D://IdeaProjects//image//uploads";
            String imgName = null;
            if (file != null) {
                File fileDir = new File(path);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                imgName = file.getOriginalFilename();
            }
            File fil = new File(path,imgName);
            file.transferTo(fil);
            Map<String, Object> map = new HashMap<>();
            map.put("imagePath",imgName);
//            System.out.println("uuuuuuu1");
            jsonDto.setId(1);
            jsonDto.setMessage("图片上传成功！");
            jsonDto.setMap(map);

            return         JSONObject.toJSONString(jsonDto);
        } catch (IOException e) {
            e.printStackTrace();
//            return DtoUtil.error(0, "图片上传失败！");
            jsonDto.setId(0);
            jsonDto.setMessage("图片上传失败！");
            return         JSONObject.toJSONString(jsonDto);
        }
    }
    //多文件上传
    @PostMapping("/uploadfiles")
    public JsonDto upload(@RequestParam("file") MultipartFile[] files) {
        JsonDto<Object> jsonDto = new JsonDto<>();
        try {
            String path = "/home//shop//image//uploads";
            String imgName = null;
            Map<String, Object> map = new HashMap<>();
            for (MultipartFile file : files) {
                if (file != null) {
                    File fileDir = new File(path);
                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }
                    imgName = file.getOriginalFilename();
                }
                File fil = new File(path,imgName);
                file.transferTo(fil);

                map.put("imagePath", "/uploads/" + imgName);
            }
            jsonDto.setId(1);
            jsonDto.setMessage("图片上传成功！");
            jsonDto.setMap(map);
            return jsonDto;
        } catch (IOException e) {
            e.printStackTrace();
            jsonDto.setId(0);
            jsonDto.setMessage("图片上传失败！");;
            return jsonDto;
        }

    }
}
