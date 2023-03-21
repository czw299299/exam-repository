package org.czw.examplay.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.czw.examplay.pojo.SubjectInf;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface SubjectMapper {
    ArrayList<SubjectInf> showall();
    Integer upsubject(SubjectInf subjectInf);
    Integer addsubject(SubjectInf subjectInf);
    Integer delsubject(@Param("sid") int sid);
}
