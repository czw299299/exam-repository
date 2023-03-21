package org.czw.examplay.service;



import org.czw.examplay.pojo.ExamInf;
import org.czw.examplay.qo.ExamQo;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.ExamVo;

import java.util.ArrayList;

public interface ExamService {
    JsonDto showexams(ExamVo examVo);
    JsonDto showexamsed(ExamVo examVo);
    JsonDto selecteid(ExamVo examVo);
    JsonDto examSo(ExamVo examVo);
    JsonDto applyexam(ExamVo examVo);
}
