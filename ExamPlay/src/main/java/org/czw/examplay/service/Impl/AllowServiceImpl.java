package org.czw.examplay.service.Impl;

import org.czw.examplay.mapper.AllowMapper;
import org.czw.examplay.pojo.AllowInf;
import org.czw.examplay.qo.AllowQo;
import org.czw.examplay.service.AllowService;
import org.czw.examplay.util.BaseResponse;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.AllowVo;
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
        ArrayList<AllowQo> allowInfs = allowMapper.selectallonline(allowVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(allowInfs);
        baseResponse.setDataTotal(allowInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入报名管理页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto allowSo(AllowVo allowVo) {
        ArrayList<AllowQo> allowInfs = allowMapper.selectallonline(allowVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(allowInfs);
        baseResponse.setDataTotal(allowInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入报名管理页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto cancleallow(AllowVo allowVo) {
        Integer delAllow = allowMapper.delAllow(allowVo);
        JsonDto jsonDto = new JsonDto();
        if (delAllow!=0){
            jsonDto.setId(1);
            jsonDto.setMessage("取消成功");
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("取消失败,需至少在开考24小时之前取消");
        }

        return jsonDto;
    }

    @Override
    public JsonDto alterAllow(AllowVo allowVo) {
        JsonDto jsonDto = new JsonDto();
        Integer integer = allowMapper.alterAllow(allowVo);
        if (integer!=0){
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set(allowVo.getAwid(),allowVo.getAwstate());
            jsonDto.setId(1);
            if (allowVo.getAwstate()==7){
                jsonDto.setMessage("退出考试");
            }else if (allowVo.getAwstate()==3){
                jsonDto.setMessage("暂停考试");
            } else if (allowVo.getAwstate()==4) {
                jsonDto.setMessage("考试中断");
            }
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("进入考试失败");
        }
        return jsonDto;
    }
    @Override
    public JsonDto marry(AllowVo allowVo) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        AllowQo allowQo = new AllowQo();
        Integer o = (Integer) opsForValue.get(allowVo.getAwid());
        Integer o1 = (Integer) opsForValue.get("eid:"+allowVo.getEid());
        System.out.println(o1);
        allowQo.setAwstate(o);
        allowQo.setEstate(o1);
        map.put("allowQo",allowQo);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }
}
