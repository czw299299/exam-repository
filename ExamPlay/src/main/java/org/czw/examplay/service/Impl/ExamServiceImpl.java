package org.czw.examplay.service.Impl;

import org.czw.examplay.mapper.AllowMapper;
import org.czw.examplay.mapper.ExamMapper;
import org.czw.examplay.pojo.AllowInf;
import org.czw.examplay.pojo.ExamInf;
import org.czw.examplay.service.ExamService;
import org.czw.examplay.util.BaseResponse;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.ExamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private AllowMapper allowMapper;

    @Override
    public JsonDto showexams(ExamVo examVo) {
        ArrayList<ExamInf> examInfs = examMapper.selectexams(examVo);
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
    public  JsonDto showexamsed(ExamVo examVo) {
        return null;
    }

    @Override
    public  JsonDto selecteid(ExamVo examVo) {
        return null;
    }

    @Override
    public JsonDto examSo(ExamVo examVo) {
        ArrayList<ExamInf> examInfs = examMapper.selectexams(examVo);
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
    public JsonDto applyexam(ExamVo examVo) {
        JsonDto jsonDto = new JsonDto();
        AllowInf allowInf = new AllowInf();
        while (true){
            Integer  awid=(int)((Math.random()*9+1)*100000000);
            ArrayList<AllowInf> allowInfs=allowMapper.selectawid(awid);
            if (allowInfs.size()==0){

                allowInf.setEeid(examVo.getEeid());
                allowInf.setEid(examVo.getEid());
                allowInf.setAwid(awid);
                break;
            }
        }
        Integer key=allowMapper.addAllow(allowInf);
        if (key!=null){
            jsonDto.setId(1);
            jsonDto.setMessage("报名成功等待审核。。。");
        }
        return jsonDto;
    }
}
