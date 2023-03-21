package org.czw.examplay.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.czw.examplay.pojo.PaperInf;
import org.czw.examplay.pojo.SubjectInf;
import org.czw.examplay.pojo.TopicInf;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@Mapper
public interface TopicMapper {
    ArrayList<TopicInf> selectsidtopic(SubjectInf subjectInf);
//    int batchInsert(@Param(value = "list") List<TTestUser> user);
    Integer addtopics(@Param(value = "list") List<TopicInf> list);
    Integer uptopic(TopicInf topicInf);
    Integer deltopic(@Param("tid") Integer tid);
    Integer deltopicsid(Integer sid);
    ArrayList<TopicInf> addselectptsid(TopicVo topicVo);
    ArrayList<TopicInf> ownselectptpid(PaperInf paperInf);

}
