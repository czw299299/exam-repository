package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.ExamMapper;
import org.czw.exammanger.pojo.AdminInf;
import org.czw.exammanger.pojo.ExamInf;
import org.czw.exammanger.pojo.PaperInf;
import org.czw.exammanger.pojo.PaperTopicInf;
import org.czw.exammanger.service.ExamService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.ExamVo;
import org.czw.exammanger.vo.PaperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JsonDto showexamsdate(ExamVo examVo) {
        ArrayList<ExamInf> examInfs = examMapper.selectexamsdate(examVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(examInfs);
        baseResponse.setDataTotal(examInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入考试管理页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto delexam(ExamVo examVo)
    {
        JsonDto jsonDto = new JsonDto();
        Integer integer =examMapper.delexam(examVo);
        if (integer!=null){
            System.out.println("delexam"+integer);
            jsonDto.setId(1);
            jsonDto.setMessage("删除成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto addexam(ExamInf examInf)
    {
        JsonDto<Object> jsonDto = new JsonDto<>();
        Integer integer =examMapper.addexam(examInf);
        System.out.println("examkey"+integer);
        HashMap map = new HashMap();
        map.put("exam",examInf);
        jsonDto.setId(1);
        jsonDto.setMessage("添加成功");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto alterexam(ExamInf examInf) {

        JsonDto jsonDto = new JsonDto();
        Integer alterexam = examMapper.alterexam(examInf);
        if (alterexam!=0){
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set("eid:"+examInf.getEid(),examInf.getEstate());
            jsonDto.setId(1);
            jsonDto.setMessage("修改成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto showexamsaid(ExamVo examVo) {
        return null;
    }

    @Override
    public JsonDto examSo(ExamVo examVo) {
        ArrayList<ExamInf> examInfs = examMapper.selectexamsdate(examVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(examInfs);
        baseResponse.setDataTotal(examInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入考试管理页面");
        jsonDto.setMap(map);
        return jsonDto;
    }
}
