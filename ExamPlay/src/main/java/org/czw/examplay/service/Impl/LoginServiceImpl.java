 package org.czw.examplay.service.Impl;


import org.czw.examplay.mapper.ExamineeMapper;
import org.czw.examplay.mapper.MenuMapper;
import org.czw.examplay.mapper.RoleMapper;
import org.czw.examplay.pojo.ExamineeInf;
import org.czw.examplay.pojo.MenuInf;
import org.czw.examplay.pojo.RoleInf;
import org.czw.examplay.service.LoginService;
import org.czw.examplay.util.CertNoUtil;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.util.JwtUtil;
import org.czw.examplay.util.MD5;
import org.czw.examplay.vo.ExamineeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ExamineeMapper examineeMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JsonDto check(ExamineeVo examineeVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();

        ValueOperations opsForValue = redisTemplate.opsForValue();
        String code = (String) opsForValue.get("codeF");
        System.out.println("验证码：" + code);
        if (code.equals(examineeVo.getCode())){
            examineeVo.getExaminee().setEepwd(MD5.getMD5String(examineeVo.getExaminee().getEepwd()));
            ExamineeInf examineeInf = examineeMapper.selectexaminee(examineeVo.getExaminee().getIc(), examineeVo.getExaminee().getEepwd());
            System.out.println("examinee:"+examineeInf);
            if (examineeInf!=null){
                opsForValue.set("uuid",examineeInf.getEeid());
                if(examineeInf.getEestate()==2){
                String token= JwtUtil.sign(examineeInf.getIc(),examineeInf.getEeid().toString());
                RoleInf loginRole = roleMapper.loginRole(examineeInf.getRid());
                ArrayList<MenuInf> menuh = menuMapper.menushow(examineeInf.getRid(), 0);
                ArrayList<MenuInf> menus = menuMapper.menushow(examineeInf.getRid(), menuh.get(0).getMid());
                map.put("role",loginRole);
                map.put("menuh",menuh);
                map.put("menus",menus);
                if (token!=null){
                    map.put("examinee",examineeInf);
                    jsonDto.setMap(map);
                    jsonDto.setId(1);
                    jsonDto.setToken(token);
                    jsonDto.setMessage("登陆成功");
                    return jsonDto;
                }
                }
                jsonDto.setMap(map);
                jsonDto.setId(0);
                jsonDto.setMessage("账号审核未通过");
                return jsonDto;

            }
            jsonDto.setMap(map);
            jsonDto.setId(0);
            jsonDto.setMessage("账号密码错误！");
            return jsonDto;
        }
        jsonDto.setMap(map);
        jsonDto.setId(0);
        jsonDto.setMessage("验证码错误！");
        return jsonDto;
    }

    @Override
    public JsonDto register(ExamineeVo examineeVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String code = (String) opsForValue.get("codeR");
        System.out.println("验证码：" + code+" "+examineeVo);
        if (code.equals(examineeVo.getCode())){
            Boolean flag= CertNoUtil.vId(examineeVo.getExaminee().getIc());
            if (flag==false){
            if (examineeVo.getEepwd2().equals(examineeVo.getExaminee().getEepwd())) {
                examineeVo.getExaminee().setEepwd(MD5.getMD5String(examineeVo.getExaminee().getEepwd()));
                Integer addexaminee = examineeMapper.addexaminee(examineeVo.getExaminee());
                if (addexaminee != null) {
                    jsonDto.setId(1);
                    jsonDto.setMessage("注册成功等待审核。。。");
                    return jsonDto;
                }
            }
            jsonDto.setId(0);
            jsonDto.setMessage("两次密码不一致！");
                return jsonDto;
            }
            jsonDto.setId(0);
            jsonDto.setMessage("身份证号码格式错误");
            return jsonDto;

    }
        jsonDto.setId(0);
        jsonDto.setMessage("验证码错误！");
        return jsonDto;
    }

}
