package org.czw.exammanger.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.czw.exammanger.pojo.ExamInf;
import org.czw.exammanger.qo.ExamQo;
import org.czw.exammanger.vo.ExamVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface ExamMapper {
    ArrayList<ExamInf> selectexamsdate(ExamVo examVo);
    Integer delexam(ExamVo examVo);
    Integer addexam(ExamInf examInf);
    Integer alterexam(ExamInf examInf);
    ArrayList<ExamQo> selectexamsaid(ExamVo examVo);

}
