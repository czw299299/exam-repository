package org.czw.exammanger.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.czw.exammanger.pojo.PaperTopicInf;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@Mapper
public interface PaperTopicMapper {
     Integer addpt(@Param(value = "list") ArrayList<PaperTopicInf> list);
     Integer delpt(@Param(value = "list") List<PaperTopicInf> list);
     Integer delptid(PaperTopicInf paperTopicInf);
}
