package org.czw.examplay.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.czw.examplay.pojo.ExamineeInf;
import org.czw.examplay.vo.ExamineeVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface ExamineeMapper {
    ExamineeInf selectexaminee(@Param("ic") String ic, @Param("eepwd") String eepwd);
    Integer  addexaminee(ExamineeInf examineeInf);
    ExamineeInf selecteeid(ExamineeInf examineeInf);
    ArrayList<ExamineeInf> selectic(@Param("ic") String eename);
    ArrayList<ExamineeInf> selectall(ExamineeInf examineeInf);
    Integer alterexaminee(ExamineeInf examineeInf);
}
