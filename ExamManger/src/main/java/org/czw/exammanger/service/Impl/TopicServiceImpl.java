package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.PaperTopicMapper;
import org.czw.exammanger.mapper.SubjectMapper;
import org.czw.exammanger.mapper.TopicMapper;
import org.czw.exammanger.pojo.PaperTopicInf;
import org.czw.exammanger.pojo.RoleInf;
import org.czw.exammanger.pojo.SubjectInf;
import org.czw.exammanger.pojo.TopicInf;
import org.czw.exammanger.service.TopicService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.TopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private PaperTopicMapper paperTopicMapper;
    @Override
    public JsonDto showtopic(TopicVo topicvo) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<TopicInf> topicInfs = topicMapper.selectsidtopic(topicvo.getSubject());
        BaseResponse<TopicInf> baseResponse = new BaseResponse<>(topicInfs);
        baseResponse.setDataTotal(topicInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入题库管理界面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto addtopic(TopicInf topicInf)
    {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<TopicInf> topicInfs = new ArrayList<>();
        topicInfs.add(topicInf);
        Integer integer = topicMapper.addtopics(topicInfs);
        if (integer!=0){
            map.put("topic",topicInf);
            jsonDto.setMap(map);
            jsonDto.setId(1);
            jsonDto.setMessage("添加成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto deltopic(Integer tid) {
        JsonDto jsonDto = new JsonDto();
        Integer integer = topicMapper.deltopic(tid);
        if (integer!=0){
            PaperTopicInf paperTopicInf = new PaperTopicInf();
            paperTopicInf.setTid(tid);
            Integer delptid = paperTopicMapper.delptid(paperTopicInf);
            System.out.println("delptid"+delptid);
            jsonDto.setId(1);
            jsonDto.setMessage("删除成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto uptopic(TopicInf topicInf) {
        JsonDto jsonDto = new JsonDto();
        Integer uptopic = topicMapper.uptopic(topicInf);
        if (uptopic!=0){
            jsonDto.setId(1);
            jsonDto.setMessage("修改成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto subjects() {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        List<SubjectInf> subjects =subjectMapper.showall();
        map.put("subjects",subjects);
        jsonDto.setId(1);
        jsonDto.setMessage("科目表");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto topicSo(TopicVo topicVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ArrayList<TopicInf> topicInfs = topicMapper.selectsidtopic(topicVo.getSubject());
        BaseResponse<TopicInf> baseResponse = new BaseResponse<>(topicInfs);
        baseResponse.setDataTotal(topicInfs.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }


}
