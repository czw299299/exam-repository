package org.czw.examplay.service;


import org.czw.examplay.qo.GradeQo;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.GradeVo;

import java.util.ArrayList;

public interface GradeService {
    JsonDto showGrades(GradeVo gradeVo);
    JsonDto addGrade(GradeVo gradeVo);
}
