package org.czw.examplay.service.Impl;

import org.czw.examplay.mapper.ExamineeMapper;
import org.czw.examplay.mapper.RoleMapper;
import org.czw.examplay.pojo.ExamineeInf;
import org.czw.examplay.service.ExamineeService;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.util.MD5;
import org.czw.examplay.vo.ExamineeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Service
public class ExamineeServiceImpl implements ExamineeService {
    @Autowired
    private ExamineeMapper examineeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JsonDto alter(ExamineeVo examineeVo) {
        System.out.println(" examineeVo:"+examineeVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String code = (String) opsForValue.get("codeF");
        System.out.println("code:"+code);
        if (code.equals(examineeVo.getCode())){
            examineeVo.getExaminee().setEepwd(MD5.getMD5String(examineeVo.getExaminee().getEepwd()));
            ExamineeInf examineeInf = examineeMapper.selectexaminee(examineeVo.getExaminee().getIc(),examineeVo.getExaminee().getEepwd());
            if (examineeInf!=null){
                examineeInf.setEepwd(MD5.getMD5String(examineeVo.getEepwd2()));
                Integer alter = examineeMapper.alterexaminee(examineeInf);
                if (alter!=null){
                    ExamineeInf examineeInf1 = examineeMapper.selectexaminee(examineeVo.getExaminee().getIc(),examineeVo.getExaminee().getEepwd());
                    map.put("examinee",examineeInf1);
                    jsonDto.setMap(map);
                    jsonDto.setId(1);
                    jsonDto.setMessage("修改成功");
                    return jsonDto;
                }
            }else {
                jsonDto.setMap(map);
                jsonDto.setId(0);
                jsonDto.setMessage("修改失败,密码错误");
                return jsonDto;
            }

        }
        jsonDto.setMap(map);
        jsonDto.setId(0);
        jsonDto.setMessage("修改失败,验证码错误");
        return jsonDto;
    }

    @Override
    public JsonDto alterImg(ExamineeVo examineeVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        Integer alterexaminee = examineeMapper.alterexaminee(examineeVo.getExaminee());
        if (alterexaminee!=0){
            map.put("eeimg",examineeVo.getExaminee().getEeimg());
            jsonDto.setMap(map);
            jsonDto.setId(1);
            jsonDto.setMessage("头像更换完成");
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("头像修改失败");
        }
        return jsonDto;
    }


    @Override
    public JsonDto re(MultipartFile file, ExamineeInf examineeInf, String pwd2, String vode, HttpSession session) {
        return null;
    }

    @Override
    public JsonDto addexaminee(MultipartFile file, ExamineeInf examineeInf, String pwd2, String vode, HttpSession session) {
        return null;
    }

    @Override
    public JsonDto showall(ExamineeVo examineeVo) {
        return null;
    }

    @Override
    public JsonDto selecteeid(ExamineeVo examineeVo) {
        return null;
    }

    @Override
    public JsonDto upexaminee(MultipartFile file, HttpSession session, ExamineeInf examineeInf) {
        return null;
    }

    @Override
    public JsonDto upexaminee2(HttpSession session, ExamineeInf examineeInf, String eepwd2) {
        return null;
    }

    @Override
    public JsonDto imgUpload(MultipartFile file, HttpSession session) throws IOException {
        return null;
    }
}
