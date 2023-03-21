package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.AdminMapper;
import org.czw.exammanger.mapper.MenuMapper;
import org.czw.exammanger.mapper.RoleMapper;
import org.czw.exammanger.pojo.AdminInf;
import org.czw.exammanger.pojo.MenuInf;
import org.czw.exammanger.pojo.RoleInf;
import org.czw.exammanger.vo.AdminVo;
import org.czw.exammanger.service.LoginService;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.util.JwtUtil;
import org.czw.exammanger.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JsonDto check(AdminVo adminQo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();

        ValueOperations opsForValue = redisTemplate.opsForValue();
        String code = (String) opsForValue.get("code");
        System.out.println("验证码：" + code);
        if (code.equals(adminQo.getCode())){
                 adminQo.getAdmin().setApwd(MD5.getMD5String(adminQo.getAdmin().getApwd()));
                 AdminInf adminInf = adminMapper.check(adminQo.getAdmin());
            System.out.println("admin:"+adminInf);
            if (adminInf!=null){
                String token= JwtUtil.sign(adminInf.getAacc(),adminInf.getAid().toString());
                RoleInf loginRole = roleMapper.loginRole(adminInf.getRid());
                ArrayList<MenuInf> menuh = menuMapper.menushow(adminInf.getRid(), 0);
                ArrayList<MenuInf> menus = menuMapper.menushow(adminInf.getRid(), menuh.get(0).getMid());
                map.put("role",loginRole);
                map.put("menuh",menuh);
                map.put("menus",menus);
                if (!token.equals(" ")){
                    map.put("admin",adminInf);
                    jsonDto.setMap(map);
                    jsonDto.setId(1);
                    jsonDto.setToken(token);
                    jsonDto.setMessage("登陆成功");
                    return jsonDto;
                }
                jsonDto.setMap(map);
                jsonDto.setId(0);
                jsonDto.setMessage("账号密码错误！");
                return jsonDto;
            }

        }

        return jsonDto;
    }
}
