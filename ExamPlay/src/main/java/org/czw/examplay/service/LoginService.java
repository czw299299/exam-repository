package org.czw.examplay.service;

import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.ExamineeVo;

public interface LoginService {
   JsonDto check(ExamineeVo examineeVo);
   JsonDto register(ExamineeVo examineeVo);
}
