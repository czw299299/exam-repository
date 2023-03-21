package org.czw.examplay.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.czw.examplay.pojo.PaperInf;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface PaperMapper {
    ArrayList<PaperInf> selectpager(PaperInf paper);
    Integer addpaper(PaperInf paper);
    Integer uppaper(PaperInf paper);
    Integer delpaperpid(Integer pid);
    Integer delpapersid(Integer sid);
}
