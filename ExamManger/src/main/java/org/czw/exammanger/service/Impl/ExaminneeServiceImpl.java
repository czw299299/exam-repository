package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.ExamineeMapper;
import org.czw.exammanger.pojo.ExamineeInf;
import org.czw.exammanger.service.ExamineeService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.ExamineeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ExaminneeServiceImpl implements ExamineeService {
    @Autowired
    private ExamineeMapper examineeMapper;
    @Override
    public JsonDto showExaminee(ExamineeVo examineeVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ArrayList<ExamineeInf> examineeInfs = examineeMapper.selectall(examineeVo.getExaminee());
        BaseResponse baseResponse = new BaseResponse<>(examineeInfs);
        baseResponse.setDataTotal(examineeInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入考生管理页面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto delexaminee(ExamineeVo examineeVo) {
        return null;
    }

    @Override
    public JsonDto alterexaminee(ExamineeInf examineeInf) {
        JsonDto jsonDto = new JsonDto();
        Integer alterexaminee = examineeMapper.alterexaminee(examineeInf);
        if (alterexaminee!=0){
            jsonDto.setId(1);
            jsonDto.setMessage("修改成功");
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("修改失败");
        }

        return jsonDto;
    }

    @Override
    public JsonDto alterexaminees(List<ExamineeInf> examineeInfs) {
        System.out.println(examineeInfs);
        JsonDto jsonDto = new JsonDto();
        Integer alterexaminees = examineeMapper.alterexaminees(examineeInfs);
        if (alterexaminees!=0){
            jsonDto.setId(1);
            jsonDto.setMessage("修改成功");
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("修改失败");
        }

        return jsonDto;
    }
}
