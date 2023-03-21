package org.czw.examplay.controller;


import com.alibaba.fastjson.JSONObject;
import com.cwq.codespringbootstarter.CodeImage;
import org.czw.examplay.pojo.AllowInf;
import org.czw.examplay.pojo.ExamineeInf;
import org.czw.examplay.service.*;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private CodeImage codeImage;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ExamineeService examineeService;
    @Autowired
    private ExamService examService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private AllowService allowService;
    @Autowired
    private  PaperService paperService;
    @Autowired
    private  GradeService gradeService;
    @RequestMapping("/initmygrades")
    public JsonDto initmygrades(@RequestBody ExamineeInf examineeInf){
        JsonDto jsonDto = new JsonDto();
        GradeVo gradeVo = new GradeVo();
        gradeVo.setEeid(examineeInf.getEeid());
        jsonDto  = gradeService.showGrades(gradeVo);
        return jsonDto;
    }
    @RequestMapping("/addGrade")
    public JsonDto addGrade(@RequestBody GradeVo gradeVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =gradeService.addGrade(gradeVo);
        return jsonDto;
    }
    @RequestMapping("/marry")
    public JsonDto marry(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =allowService.marry(allowVo);
        return jsonDto;
    }
    @RequestMapping("/alterallow")
    public JsonDto alterallow(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = allowService.alterAllow(allowVo);
        return jsonDto;
    }
    @RequestMapping("/owntopics")
    public JsonDto  ownTopics(@RequestBody PaperVo paperVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = paperService.ownTopics(paperVo);
        return jsonDto;
    }
    @RequestMapping("/cancleallow")
    public JsonDto cancleallow(@RequestBody AllowVo allowVo){
        System.out.println("allowVo"+allowVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = allowService.cancleallow(allowVo);
        return jsonDto;
    }
    @RequestMapping("/allowSo")
    public JsonDto allowSo(@RequestBody AllowVo allowVo){
        System.out.println("allowVo"+allowVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = allowService.allowSo(allowVo);
        return jsonDto;
    }
    @RequestMapping("/initallow")
    public JsonDto initallow(@RequestBody ExamineeInf examineeInf){
        JsonDto jsonDto = new JsonDto();
        AllowVo allowVo = new AllowVo();
        allowVo.setEeid(examineeInf.getEeid());
        jsonDto  = allowService.showallows(allowVo);
        return jsonDto;
    }
    @RequestMapping("/applyexam")
    public JsonDto applyexam(@RequestBody ExamVo examVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = examService.applyexam(examVo);
        return jsonDto;
    }
    @RequestMapping("/subjects")
    public JsonDto subjects(){
        JsonDto jsonDto = new JsonDto();
        jsonDto = subjectService.subjects();
        return jsonDto;
    }
    @RequestMapping("/examiners")
    public JsonDto examiners(){
        JsonDto jsonDto = new JsonDto();
        jsonDto = adminService.examiners();
        return jsonDto;
    }
    @RequestMapping("/examSo")
    public JsonDto examSo(@RequestBody ExamVo examVo){
        System.out.println("examVo"+examVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = examService.examSo(examVo);
        return jsonDto;
    }
    @RequestMapping("/initapplyexam")
    public JsonDto initExam(@RequestBody ExamineeInf examineeInf){
        JsonDto jsonDto = new JsonDto();
        ExamVo examVo = new ExamVo();
        examVo.setEeid(examineeInf.getEeid());
        jsonDto  = examService.showexams(examVo);
        return jsonDto;
    }
    @RequestMapping("/register")
    public JsonDto register(@RequestPart("file") MultipartFile file,@RequestPart("examineeVo") String examineeVoStr){
        ExamineeVo examineeVo = JSONObject.parseObject(examineeVoStr, ExamineeVo.class);
        System.out.println("ExamineeInf"+examineeVo.getExaminee());
        JsonDto jsonDto =loginService.register(examineeVo);
        upload(file,examineeVo.getExaminee().getEeid()+"");
        return jsonDto;
    }
    @RequestMapping("/login")
    public JsonDto login(@RequestBody ExamineeVo examineeVo){
        System.out.println("ExamineeInf"+examineeVo.getExaminee());
        JsonDto jsonDto = loginService.check(examineeVo);
        return jsonDto;
    }
    @RequestMapping("/alterExaminee")
    public JsonDto alterAdmin(@RequestBody ExamineeVo examineeVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto=examineeService.alter(examineeVo);
        return jsonDto;
    }

    @RequestMapping("/codeF")
    public void codeF(HttpSession session, HttpServletResponse resp) throws IOException {
        HashMap map = codeImage.testImg(session, resp);
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("codeF",map.get("rand"));
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        ImageIO.write((RenderedImage) map.get("imageIo"),"JPEG",responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @RequestMapping("/codeR")
    public void codeR(HttpSession session, HttpServletResponse resp) throws IOException {
        HashMap map = codeImage.testImg(session, resp);
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("codeR",map.get("rand"));
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        ImageIO.write((RenderedImage) map.get("imageIo"),"JPEG",responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @PostMapping("/upload")
    public JsonDto upload(@RequestPart("file") MultipartFile file,@RequestPart("eeid") String eeid){
        System.out.println("eeid"+eeid);
        JsonDto<Object> jsonDto = new JsonDto<>();
        try {
            String url="http://localhost:8888/putfile/upload";
            //封装请求头
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE + ";charset=UTF-8");


            //文件处理
            FileSystemResource resource = new FileSystemResource(multipartFile2File(file));
            //表单处理
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("file", resource);


            HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            //发送请求
            JSONObject jsonObject = restTemplate.postForObject(url, files, JSONObject.class);
//            for (Object value : jsonObject.values()) {
//                System.out.println(value);
//            }
            JsonDto dto = JSONObject.parseObject(jsonObject.toJSONString(),JsonDto.class);
//            DtoUtil dtoUtil = JSONObject.parseObject(jsonObject.toJSONString(), DtoUtil.class);
            System.out.println(dto);
            String imageName = dto.getMap().get("imagePath").toString();
            System.out.println(imageName);
            Map<String,Object> map = new HashMap<>();
            ExamineeVo examineeVo = new ExamineeVo();
            ExamineeInf examineeInf = new ExamineeInf();
            examineeInf.setEeimg(imageName);
            examineeInf.setEeid(Integer.valueOf(eeid));
            examineeVo.setExaminee(examineeInf);
            jsonDto= examineeService.alterImg(examineeVo);
            return jsonDto;
        } catch(Exception e){
            e.printStackTrace();
        }
        jsonDto.setId(0);
        jsonDto.setMessage("图片上传失败！");
//        return DtoUtil.error(0,"图片上传失败！");
        return jsonDto;
    }

    private static File multipartFile2File(@NonNull MultipartFile multipartFile) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件前缀
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        //获取文件后缀
//        if(prefix.length()<3){
//            DtoUtil.error(0,"图片名字至少为3个字符！");
//        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        try {
            //生成临时文件
            File file = File.createTempFile(prefix, suffix);
            //将原文件转换成新建的临时文件
            multipartFile.transferTo(file);
            file.deleteOnExit();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
