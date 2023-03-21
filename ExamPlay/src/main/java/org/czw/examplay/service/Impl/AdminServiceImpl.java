package org.czw.examplay.service.Impl;


import org.czw.examplay.mapper.AdminMapper;
import org.czw.examplay.mapper.RoleMapper;
import org.czw.examplay.pojo.AdminInf;
import org.czw.examplay.service.AdminService;
import org.czw.examplay.util.JsonDto;
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
    public JsonDto examiners() {
        List<AdminInf> adminInfs = adminMapper.allexaminer();
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        map.put("examiners",adminInfs);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }


}
