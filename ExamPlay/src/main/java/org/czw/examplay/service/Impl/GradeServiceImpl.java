package org.czw.examplay.service.Impl;

import org.czw.examplay.mapper.GradeMapper;
import org.czw.examplay.qo.AllowQo;
import org.czw.examplay.qo.GradeQo;
import org.czw.examplay.service.GradeService;
import org.czw.examplay.util.BaseResponse;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.GradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;
    @Override
    public JsonDto showGrades(GradeVo gradeVo) {
        ArrayList<GradeQo> gradeQos = gradeMapper.selectGrades(gradeVo);
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(gradeQos);
        baseResponse.setDataTotal(gradeQos.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入个人成绩页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto addGrade(GradeVo gradeVo) {
        JsonDto<Object> jsonDto = new JsonDto<>();
        Integer integer = gradeMapper.addGrade(gradeVo);
        if (integer!=0){
            jsonDto.setId(1);
        }
        return jsonDto;
    }
}
