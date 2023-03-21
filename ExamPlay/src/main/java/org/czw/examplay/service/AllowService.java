package org.czw.examplay.service;

import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.AllowVo;

public interface  AllowService {
    JsonDto showallows(AllowVo allowVo);
    JsonDto allowSo(AllowVo allowVo);
    JsonDto cancleallow(AllowVo allowVo);
    JsonDto alterAllow(AllowVo allowVo);
    JsonDto marry(AllowVo allowVo);
}
