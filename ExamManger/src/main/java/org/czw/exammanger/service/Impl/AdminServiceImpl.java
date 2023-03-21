package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.AdminMapper;
import org.czw.exammanger.mapper.RoleMapper;
import org.czw.exammanger.pojo.AdminInf;
import org.czw.exammanger.pojo.RoleInf;
import org.czw.exammanger.vo.AdminVo;
import org.czw.exammanger.service.AdminService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JsonDto alter(AdminVo adminQo) {
        System.out.println("adminQo:"+adminQo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String code = (String) opsForValue.get("code");
        System.out.println("code:"+code);
        if (code.equals(adminQo.getCode())){
            adminQo.getAdmin().setApwd(MD5.getMD5String(adminQo.getAdmin().getApwd()));
            AdminInf adminInf = adminMapper.check(adminQo.getAdmin());
            if (adminInf!=null){
                adminInf.setApwd(MD5.getMD5String(adminQo.getApwd2()));
                Integer alter = adminMapper.alter(adminInf);
                if (alter!=null){
                    AdminInf adminInf1 = adminMapper.check(adminInf);
                    map.put("admin",adminInf1);
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
    public JsonDto initAdmin() {
        List<AdminInf> adminInfs = adminMapper.allAdmin();
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(adminInfs);
        baseResponse.setDataTotal(adminInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入管理员管理");
        jsonDto.setMap(map);
        return jsonDto;
    }
    @Override
    public JsonDto initExaminer() {
        List<AdminInf> adminInfs = adminMapper.allexaminer();
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(adminInfs);
        baseResponse.setDataTotal(adminInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入个人页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto examiners() {
        List<AdminInf> adminInfs = adminMapper.allexaminer();
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        map.put("examiners",adminInfs);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }

    public JsonDto roles(){
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        List<RoleInf> roles = roleMapper.roles();
        map.put("roles",roles);
        jsonDto.setId(1);
        jsonDto.setMessage("角色表");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto delAdmin(AdminVo adminQo) {
        JsonDto jsonDto = new JsonDto();
        Integer integer = adminMapper.delAdmin(adminQo.getAdmin().getAid());
        if (integer!=0) {
            jsonDto.setId(1);
            jsonDto.setMessage("删除成功");
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("删除失败");
        }
        return jsonDto;
    }

    @Override
    public JsonDto alterAdmins(AdminVo adminQo) {
        JsonDto jsonDto = new JsonDto();
        if (adminQo.getAdmin().getApwd()!=null){
        adminQo.getAdmin().setApwd(MD5.getMD5String(adminQo.getAdmin().getApwd()));
        }
        Integer integer = adminMapper.alter(adminQo.getAdmin());
        if(integer!=0){
        jsonDto.setId(1);
        jsonDto.setMessage("修改成功");
        return jsonDto;}
        else {
            jsonDto.setId(0);
            jsonDto.setMessage("修改失败");
            return jsonDto;
        }
    }

    @Override
    public JsonDto addAdmin(AdminVo adminQo) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        adminQo.getAdmin().setApwd(MD5.getMD5String(adminQo.getAdmin().getApwd()));
        Integer integer = adminMapper.addadmin(adminQo.getAdmin());
        if (integer!=0){
            map.put("admin",adminQo.getAdmin());
            jsonDto.setId(1);
            jsonDto.setMap(map);
            jsonDto.setMessage("添加成功");
        }
        else {
            jsonDto.setId(0);
            jsonDto.setMessage("添加失败");
        }
        return jsonDto;
    }
}
