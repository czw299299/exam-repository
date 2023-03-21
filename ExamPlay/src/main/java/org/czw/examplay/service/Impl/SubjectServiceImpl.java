package org.czw.examplay.service.Impl;

import org.czw.examplay.mapper.SubjectMapper;
import org.czw.examplay.pojo.SubjectInf;
import org.czw.examplay.service.SubjectService;
import org.czw.examplay.util.JsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectMapper subjectMapper;
    @Override
    public JsonDto subjects() {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<SubjectInf> showall = subjectMapper.showall();
        map.put("subjects",showall);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }


}
