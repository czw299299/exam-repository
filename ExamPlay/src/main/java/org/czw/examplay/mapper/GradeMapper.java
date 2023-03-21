package org.czw.examplay.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.czw.examplay.qo.GradeQo;
import org.czw.examplay.vo.GradeVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface GradeMapper {
    ArrayList<GradeQo> selectGrades(GradeVo gradeVo);
    Integer addGrade(GradeVo gradeVo);
}
