package org.czw.exammanger.service;



import org.czw.exammanger.pojo.ExamineeInf;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.ExamineeVo;

import java.util.ArrayList;
import java.util.List;

public interface ExamineeService {
  JsonDto showExaminee(ExamineeVo examineeVo);
    JsonDto  delexaminee(ExamineeVo examineeVo);
    JsonDto  alterexaminee(ExamineeInf examineeInf);
  JsonDto  alterexaminees(List<ExamineeInf> examineeInfs);
}
