package org.czw.exammanger.service;

import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.AllowVo;

public interface AllowService {
    JsonDto showallows(AllowVo allowVo);
    JsonDto alterallow(AllowVo allowVo);
    JsonDto joinallows(AllowVo allowVo);
    JsonDto marry(AllowVo allowVo);
}
