package org.czw.exammanger.service;

import org.czw.exammanger.vo.AdminVo;
import org.czw.exammanger.util.JsonDto;

public interface LoginService {
   JsonDto check(AdminVo adminQo);
}
