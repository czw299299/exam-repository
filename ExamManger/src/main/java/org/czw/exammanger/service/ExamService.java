package org.czw.exammanger.service;



import org.czw.exammanger.pojo.ExamInf;
import org.czw.exammanger.qo.ExamQo;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.ExamVo;
import org.czw.exammanger.vo.PaperVo;

import java.util.ArrayList;

public interface ExamService {
   JsonDto showexamsdate(ExamVo examVo);
    JsonDto delexam(ExamVo examVo);
    JsonDto addexam(ExamInf examInf);
    JsonDto alterexam(ExamInf examInf);
    JsonDto showexamsaid(ExamVo examVo);
    JsonDto examSo(ExamVo examVo);
}
