package org.czw.exammanger.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.czw.exammanger.pojo.ExamineeInf;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Mapper
public interface ExamineeMapper {
    ArrayList<ExamineeInf> selectall(ExamineeInf examineeInf);
    Integer delexaminee(ExamineeInf examineeInf);
    Integer alterexaminee(ExamineeInf examineeInf);
    Integer alterexaminees(@Param(value = "list") List<ExamineeInf> list);
}
