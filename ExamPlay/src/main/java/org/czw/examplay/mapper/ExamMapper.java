package org.czw.examplay.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.czw.examplay.pojo.ExamInf;
import org.czw.examplay.qo.ExamQo;
import org.czw.examplay.vo.ExamVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface ExamMapper {
    ArrayList<ExamInf> selectexams(ExamVo examVo);
    ArrayList<ExamInf> selectexamsed(ExamInf examInf);
    ExamInf selecteid(ExamInf examInf);

}
