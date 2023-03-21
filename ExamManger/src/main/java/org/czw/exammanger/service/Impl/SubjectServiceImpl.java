package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.PaperMapper;
import org.czw.exammanger.mapper.SubjectMapper;
import org.czw.exammanger.mapper.TopicMapper;
import org.czw.exammanger.pojo.SubjectInf;
import org.czw.exammanger.service.SubjectService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Override
    public JsonDto subjects() {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<SubjectInf> showall = subjectMapper.showall();
        BaseResponse baseResponse = new BaseResponse(showall);
        baseResponse.setDataTotal(showall.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入科目管理界面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto addsubject(SubjectInf subjectInf) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        Integer integer = subjectMapper.addsubject(subjectInf);
        if (integer!=0){
            map.put("subject",subjectInf);
            jsonDto.setMap(map);
            jsonDto.setId(1);
            jsonDto.setMessage("添加成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto delsubject(Integer sid) {
        JsonDto jsonDto = new JsonDto();
        Integer integer = subjectMapper.delsubject(sid);
        if (integer!=0){
            Integer deltopicsid = topicMapper.deltopicsid(sid);
            Integer delpapersid = paperMapper.delpapersid(sid);
            System.out.println("deltopicsid"+deltopicsid);
            System.out.println("delpapersid"+delpapersid);
            jsonDto.setId(1);
            jsonDto.setMessage("删除成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto upsubject(SubjectInf subjectInf) {
        JsonDto jsonDto = new JsonDto();
        Integer integer = subjectMapper.upsubject(subjectInf);
        if (integer!=0){
            jsonDto.setId(1);
            jsonDto.setMessage("修改成功");
        }

        return jsonDto;
    }
}
