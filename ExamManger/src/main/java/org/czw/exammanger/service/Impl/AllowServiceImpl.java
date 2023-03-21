package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.AllowMapper;
import org.czw.exammanger.pojo.ExamInf;
import org.czw.exammanger.qo.AllowQo;
import org.czw.exammanger.service.AllowService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.AllowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class AllowServiceImpl implements AllowService {
    @Autowired
    private AllowMapper allowMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JsonDto showallows(AllowVo allowVo) {
        ArrayList<AllowQo> allowQos = allowMapper.showallowonline(allowVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(allowQos);
        baseResponse.setDataTotal(allowQos.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入报考管理页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto alterallow(AllowVo allowVo) {
        JsonDto jsonDto = new JsonDto();
        Integer alterexam = allowMapper.alterAllow(allowVo);
        if (alterexam!=0){
            jsonDto.setId(1);
            jsonDto.setMessage("修改成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto joinallows(AllowVo allowVo) {
        ArrayList<AllowQo> allowQos = allowMapper.joinallowonline(allowVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        for (int i = 0; i <allowQos.size() ; i++) {
            opsForValue.set(allowQos.get(i).getAwid(),allowQos.get(i).getAwstate());
        }
        BaseResponse baseResponse = new BaseResponse<>(allowQos);
        baseResponse.setDataTotal(allowQos.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入准考证页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto marry(AllowVo allowVo) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i <allowVo.getAllowQos().size() ; i++) {
            Integer o = (Integer) opsForValue.get(allowVo.getAllowQos().get(i).getAwid());
            if (o!= allowVo.getAllowQos().get(i).getAwstate()){
                 allowVo.getAllowQos().get(i).setAwstate(o);
            }
            Integer o1= (Integer) opsForValue.get("eid:"+allowVo.getAllowQos().get(i).getEid());
               allowVo.getAllowQos().get(i).setEstate(o1);
        }
        System.out.println("ww"+allowVo.getAllowQos());
        map.put("allowQos",allowVo.getAllowQos());
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }
}
