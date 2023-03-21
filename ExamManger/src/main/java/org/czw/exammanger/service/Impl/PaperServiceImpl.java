package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.PaperMapper;
import org.czw.exammanger.mapper.PaperTopicMapper;
import org.czw.exammanger.mapper.SubjectMapper;
import org.czw.exammanger.mapper.TopicMapper;
import org.czw.exammanger.pojo.*;
import org.czw.exammanger.service.PaperService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.MenuVo;
import org.czw.exammanger.vo.PaperVo;
import org.czw.exammanger.vo.TopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private PaperTopicMapper paperTopicMapper;
    @Override
    public JsonDto showPaper(PaperVo paperVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<PaperInf> selectpager = paperMapper.selectpager(paperVo.getPaper());
        BaseResponse<PaperInf> baseResponse = new BaseResponse<>(selectpager);
        baseResponse.setDataTotal(selectpager.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入考卷管理界面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto addpaper(PaperVo paperVo) {

        Integer integer = paperMapper.addpaper(paperVo.getPaper());
        System.out.println("paperkey"+integer);
        JsonDto jsonDto = altertopics(paperVo);
        HashMap map = new HashMap();
        map.put("paper",paperVo.getPaper());
        jsonDto.setMessage("添加成功");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto alterpaper(PaperVo paperVo) {
        Integer alterRole = paperMapper.uppaper(paperVo.getPaper());
        JsonDto jsonDto = new JsonDto();
        jsonDto = altertopics(paperVo);
        return jsonDto;
    }

    @Override
    public JsonDto altertopics(PaperVo paperVo)
    {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
//        MenuVo menuVo = new MenuVo();
        TopicVo topicVo = new TopicVo();
//        menuVo.setRid(roleVo.getRole().getRid());
        topicVo.setPid(paperVo.getPaper().getPid());
//        menuVo.setMpid(1);
        ArrayList<PaperTopicInf> addPaperTopics = new ArrayList<>();
        ArrayList<PaperTopicInf> delPaperTopics = new ArrayList<>();
//        ArrayList<MenuInf> ownpowers = menuMapper.ownpowers(menuVo);
        ArrayList<TopicInf> ownselectptpid = topicMapper.ownselectptpid(paperVo.getPaper());
        for (int i = 0; i <ownselectptpid.size(); i++) {
            Integer key=0;
//            RoleMenuInf roleMenuInf = new RoleMenuInf();
            PaperTopicInf paperTopicInf = new PaperTopicInf();
            for (int j = 0; j <paperVo.getTopics().size() ; j++) {
                if (ownselectptpid.get(i).getTid()==paperVo.getTopics().get(j).getTid())
                {
                    key=1;
                }
            }
            if (key==0){
                paperTopicInf.setPid(paperVo.getPaper().getPid());
                paperTopicInf.setTid(ownselectptpid.get(i).getTid());
//                delroleMenuInfs.add(roleMenuInf);
                delPaperTopics.add(paperTopicInf);
            }
        }
        for (int i = 0; i <paperVo.getTopics().size(); i++) {
            Integer key=0;
//            RoleMenuInf roleMenuInf = new RoleMenuInf();
            PaperTopicInf paperTopicInf = new PaperTopicInf();
            for (int j = 0; j <ownselectptpid.size() ; j++) {
                if (ownselectptpid.get(j).getTid()==paperVo.getTopics().get(i).getTid())
                {
                    key=1;
                }
            }
            if (key==0){
                paperTopicInf.setPid(paperVo.getPaper().getPid());
                paperTopicInf.setTid(paperVo.getTopics().get(i).getTid());
                addPaperTopics.add(paperTopicInf);
            }
        }
        System.out.println("add"+addPaperTopics);
        System.out.println("del"+delPaperTopics);
        if (addPaperTopics.size()!=0){
            Integer integer = paperTopicMapper.addpt(addPaperTopics);
            System.out.println("key"+integer);
        }

        if (delPaperTopics.size()!=0) {
            Integer integer = paperTopicMapper.delpt(delPaperTopics);
            System.out.println("key1"+integer);
        }
        jsonDto.setId(1);
        jsonDto.setMessage("试卷修改成功");
        return jsonDto;
    }

    @Override
    public JsonDto delpaper(Integer pid) {
        JsonDto jsonDto = new JsonDto();
        Integer key = paperMapper.delpaperpid(pid);
        if (key!=0){
            PaperTopicInf paperTopicInf = new PaperTopicInf();
            paperTopicInf.setPid(pid);
            Integer delptid = paperTopicMapper.delptid(paperTopicInf);
            System.out.println("delptid"+delptid);
            jsonDto.setId(1);
            jsonDto.setMessage("删除成功");
        }
        return jsonDto;
    }

    @Override
    public JsonDto paperSo(PaperVo paperVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<PaperInf> selectpager = paperMapper.selectpager(paperVo.getPaper());
        BaseResponse<PaperInf> baseResponse = new BaseResponse<>(selectpager);
        baseResponse.setDataTotal(selectpager.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMessage("进入考卷管理界面");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto sidTopic(PaperVo paperVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<TopicInf> topicInfs = topicMapper.selectsidtopic(paperVo.getSubject());
        map.put("topics",topicInfs);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto ownTopics(PaperVo paperVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<TopicInf> ownselectptsid = topicMapper.ownselectptpid(paperVo.getPaper());
        map.put("topics",ownselectptsid);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto papers(PaperVo paperVo) {
        ArrayList<PaperInf> paperInfs = paperMapper.selectpager(paperVo.getPaper());
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        map.put("papers",paperInfs);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }
}
