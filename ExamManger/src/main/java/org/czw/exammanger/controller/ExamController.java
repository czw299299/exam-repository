package org.czw.exammanger.controller;

import com.alibaba.fastjson.JSONObject;
import com.cwq.codespringbootstarter.CodeImage;
import org.czw.exammanger.pojo.AdminInf;
import org.czw.exammanger.pojo.ExamInf;
import org.czw.exammanger.pojo.ExamineeInf;
import org.czw.exammanger.pojo.RoleInf;
import org.czw.exammanger.qo.AllowQo;
import org.czw.exammanger.service.*;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.vo.*;
import org.czw.exammanger.util.JsonDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ImportTemplateService importTemplateService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamineeService examineeService;
    @Autowired
    private AllowService allowService;
    @RequestMapping("/initlogs")
    public JsonDto initlogs(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        return jsonDto;
    }
    @RequestMapping("/initcounts")
    public JsonDto initcounts(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        return jsonDto;
    }
    @RequestMapping("/marry")
    public JsonDto marry(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =allowService.marry(allowVo);
        return jsonDto;
    }

    @RequestMapping("/onlineallowSo")
    public JsonDto onlineallowSo(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =allowService.joinallows(allowVo);
        return jsonDto;
    }

    @RequestMapping("/joinexam")
    public JsonDto joinexam(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =allowService.joinallows(allowVo);
        ValueOperations opsForValue = redisTemplate.opsForValue();
        BaseResponse datas = (BaseResponse) jsonDto.getMap().get("datas");
        for (int i = 0; i <datas.getDataTotal() ; i++) {
            ArrayList<AllowQo> list = (ArrayList<AllowQo>) datas.getDataList();
            opsForValue.set(list.get(i).getAwid(),list.get(i).getAwstate());
        }
        return jsonDto;
    }

    @RequestMapping("/initstartexam")
    public JsonDto initstartexam(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        ExamVo examVo = new ExamVo();
        examVo.setAid(adminInf.getAid());
        jsonDto  =examService.showexamsdate(examVo);
        return jsonDto;
    }
    @RequestMapping("/alterestate")
    public JsonDto alterestate(@RequestBody ExamInf examInf){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =examService.alterexam(examInf);
        return jsonDto;
    }

    @RequestMapping("/alterallow")
    public JsonDto alterallow(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =allowService.alterallow(allowVo);
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(allowVo.getAwid(),allowVo.getAwstate());
        return jsonDto;
    }
    @RequestMapping("/allowSo")
    public JsonDto allowSo(@RequestBody AllowVo allowVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  =allowService.showallows(allowVo);
        return jsonDto;
    }
    @RequestMapping("/initallowmanger")
    public JsonDto initallowmanger(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        AllowVo allowVo = new AllowVo();
        allowVo.setAwstate(1);
        jsonDto  =allowService.showallows(allowVo);
        return jsonDto;
    }
    @RequestMapping("/alterexaminee")
    public JsonDto alterexaminee(@RequestBody ExamineeInf examineeInf){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = examineeService.alterexaminee(examineeInf);
        return jsonDto;
    }
    @RequestMapping("/alterexaminees")
    public JsonDto alterexaminees(@RequestBody ExamineeVo examineeVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = examineeService.alterexaminees(examineeVo.getExaminees());
        return jsonDto;
    }
    @RequestMapping("/examineeSo")
    public JsonDto examineeSo(@RequestBody ExamineeVo examineeVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = examineeService.showExaminee(examineeVo);
        return jsonDto;
    }
    @RequestMapping("/initexaminee")
    public JsonDto initExaminee(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        ExamineeVo examineeVo = new ExamineeVo();
        ExamineeInf examineeInf = new ExamineeInf();
        examineeVo.setExaminee(examineeInf);
        jsonDto  = examineeService.showExaminee(examineeVo);
        return jsonDto;
    }
    @RequestMapping("/addexam")
    public JsonDto addExam(@RequestBody ExamInf examInf){
        System.out.println(examInf);
        JsonDto jsonDto = new JsonDto();
        jsonDto = examService.addexam(examInf);
        return jsonDto;
    }
    @RequestMapping("/alterexam")
    public JsonDto alterExam(@RequestBody ExamInf examInf){
        System.out.println(examInf);
        JsonDto jsonDto = new JsonDto();
//        jsonDto = roleService.addRole(roleVo);
        jsonDto = examService.alterexam(examInf);
        return jsonDto;
    }
    @RequestMapping("/delexam")
    public JsonDto delExam(@RequestBody ExamVo examVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = examService.delexam(examVo);
        return jsonDto;
    }

    @RequestMapping("/examSo")
    public JsonDto examSo(@RequestBody ExamVo examVo){
        System.out.println("examVo"+examVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = examService.examSo(examVo);
        return jsonDto;
    }

    @RequestMapping("/initexam")
    public JsonDto initExam(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        ExamVo examVo = new ExamVo();
        jsonDto  = examService.showexamsdate(examVo);
        return jsonDto;
    }
    @RequestMapping("/papers")
    public JsonDto Papers(@RequestBody PaperVo paperVo){
        System.out.println(paperVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto  = paperService.papers(paperVo);
        return jsonDto;
    }
    @RequestMapping("/examiners")
    public JsonDto examiners(){
        JsonDto jsonDto = new JsonDto();
        jsonDto = adminService.examiners();
        return jsonDto;
    }
    @RequestMapping("/first")
    public JsonDto first(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        jsonDto = adminService.initExaminer();
        return jsonDto;
    }
    @RequestMapping("/addpaper")
    public JsonDto addPaper(@RequestBody PaperVo paperVo){
        System.out.println(paperVo);
        JsonDto jsonDto = new JsonDto();
//        jsonDto = roleService.addRole(roleVo);
        jsonDto = paperService.addpaper(paperVo);
        return jsonDto;
    }
    @RequestMapping("/alterpaper")
    public JsonDto alterPaper(@RequestBody PaperVo paperVo){
        System.out.println(paperVo);
        JsonDto jsonDto = new JsonDto();
//        jsonDto = roleService.addRole(roleVo);
        jsonDto = paperService.alterpaper(paperVo);
        return jsonDto;
    }
    @RequestMapping("/owntopics")
    public JsonDto  ownTopics(@RequestBody PaperVo paperVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = paperService.ownTopics(paperVo);
        return jsonDto;
    }
    @RequestMapping("/sidtopic")
    public JsonDto sidTopic(@RequestBody PaperVo paperVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = paperService.sidTopic(paperVo);
        return jsonDto;
    }
    @RequestMapping("/delpaper")
    public JsonDto delPaper(@RequestBody PaperVo paperVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = paperService.delpaper(paperVo.getPaper().getPid());
        return jsonDto;
    }
    @RequestMapping("/paperSo")
    public JsonDto paperSo(@RequestBody PaperVo paperVo){
        System.out.println("paperVo"+paperVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = paperService.paperSo(paperVo);
        return jsonDto;
    }
    @RequestMapping("/initpaper")
    public JsonDto initPaper(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        PaperVo paperVo = new PaperVo();
        jsonDto  = paperService.showPaper(paperVo);
        return jsonDto;
    }
    @RequestMapping("/topicSo")
    public JsonDto topicSo(@RequestBody TopicVo topicVo){
        System.out.println("topicVo"+topicVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = topicService.topicSo(topicVo);
        return jsonDto;
    }
    @RequestMapping("/subjects")
    public JsonDto subjects(){
        JsonDto jsonDto = new JsonDto();
        jsonDto = topicService.subjects();
        return jsonDto;
    }
    @RequestMapping("/addtopic")
    public JsonDto addTopic(@RequestBody TopicVo topicVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = topicService.addtopic(topicVo.getTopic());
        return jsonDto;
    }
    @RequestMapping("/altertopic")
    public JsonDto alterTopic(@RequestBody TopicVo topicVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = topicService.uptopic(topicVo.getTopic());
        return jsonDto;
    }
    @RequestMapping("/deltopic")
    public JsonDto delTopic(@RequestBody TopicVo topicVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = topicService.deltopic(topicVo.getTopic().getTid());
        return jsonDto;
    }
    @RequestMapping("/inittopic")
    public JsonDto initTopic(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        TopicVo topicVo = new TopicVo();
        jsonDto  = topicService.showtopic(topicVo);
        return jsonDto;
    }
    @PostMapping("/uploadtipics")
    public JsonDto uploadtipics(@RequestPart("file") MultipartFile file){
        System.out.println("file"+file.getOriginalFilename());
        JsonDto jsonDto = new JsonDto();
        jsonDto  = importTemplateService.importTemplate(file);
        return jsonDto;
    }
    @RequestMapping("/delSubject")
    public JsonDto delSubject(@RequestBody SubjectVo subjectVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = subjectService.delsubject(subjectVo.getSubject().getSid());
        return jsonDto;
    }
    @RequestMapping("/addSubject")
    public JsonDto addSubject(@RequestBody SubjectVo subjectVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = subjectService.addsubject(subjectVo.getSubject());
        return jsonDto;
    }
    @RequestMapping("/alterSubject")
    public JsonDto alterSubject(@RequestBody SubjectVo subjectVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = subjectService.upsubject(subjectVo.getSubject());
        return jsonDto;
    }
    @RequestMapping("/initsubject")
    public JsonDto initSubject(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = subjectService.subjects();
        return jsonDto;
    }
    @RequestMapping("/delRole")
    public JsonDto delRole(@RequestBody RoleInf roleInf){
        System.out.println(roleInf);
        RoleVo roleVo = new RoleVo();
        roleVo.setRole(roleInf);
        JsonDto jsonDto = new JsonDto();
        jsonDto = roleService.delRole(roleVo);
        return jsonDto;
    }
    @RequestMapping("/addRole")
    public JsonDto addRole(@RequestBody RoleVo roleVo){
        System.out.println(roleVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = roleService.addRole(roleVo);
        return jsonDto;
    }
    @RequestMapping("/alterRole")
    public JsonDto alterRole(@RequestBody RoleVo roleVo){
        System.out.println(roleVo);
        JsonDto jsonDto = new JsonDto();
        jsonDto = roleService.alterRole(roleVo);
        return jsonDto;
    }
    @RequestMapping("/ownpowers")
    public JsonDto allpowers(@RequestBody MenuVo menuVo){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = roleService.ownpowers(menuVo);
        return jsonDto;
    }
    @RequestMapping("/allpowers")
    public JsonDto allpowers(){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = roleService.allpowers();
        return jsonDto;
    }
    @RequestMapping("/initrole")
    public JsonDto initRole(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        jsonDto  = roleService.initRole();
        return jsonDto;
    }
    @RequestMapping("/addAdmin")
    public JsonDto addAdmin(@RequestBody AdminVo adminQo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = adminService. addAdmin(adminQo);
        return jsonDto;
    }

    @RequestMapping("/alterAdmins")
    public JsonDto alterAdmins(@RequestBody AdminVo adminQo){
        JsonDto jsonDto = new JsonDto();
        jsonDto = adminService. alterAdmins(adminQo);
        return jsonDto;
    }
     @RequestMapping("/delAdmin")
     public JsonDto delAdmin(@RequestBody AdminVo adminQo){
         JsonDto jsonDto = new JsonDto();
         jsonDto  = adminService.delAdmin(adminQo);
         return jsonDto;
     }
    @RequestMapping("/roles")
    public JsonDto roles(){
        JsonDto jsonDto = new JsonDto();
        jsonDto = adminService.roles();
        return jsonDto;
    }
    @RequestMapping("/initadmin")
    public JsonDto  initAdmin(@RequestBody AdminInf adminInf){
        JsonDto jsonDto = new JsonDto();
        jsonDto=adminService.initAdmin();
        return jsonDto;
    }
    @RequestMapping("/alterAdmin")
    public JsonDto alterAdmin(@RequestBody AdminVo adminQo){
        JsonDto jsonDto = new JsonDto();
        jsonDto= adminService.alter(adminQo);
        return jsonDto;
    }
    @RequestMapping("/login")
    public JsonDto login(@RequestBody AdminVo adminQo){
        System.out.println("admin"+adminQo);
        JsonDto jsonDto = loginService.check(adminQo);
        return jsonDto;
    }

    @RequestMapping("/code")
    public void code(HttpSession session, HttpServletResponse resp) throws IOException {
        HashMap map = codeImage.testImg(session, resp);
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("code",map.get("rand"));
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        ImageIO.write((RenderedImage) map.get("imageIo"),"JPEG",responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @PostMapping("/upload")
    public JsonDto upload(@RequestPart("file") MultipartFile file){
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
            map.put("imagePath", "http://localhost:8888/image" + imageName);
            map.put("imagePath",imageName);
//            return DtoUtil.sucess(1, "图片上传成功！", "", map);
            jsonDto.setId(1);
            jsonDto.setMessage("图片上传成功！");
            jsonDto.setMap(map);
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
