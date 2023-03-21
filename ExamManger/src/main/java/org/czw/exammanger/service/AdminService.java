package org.czw.exammanger.service;

import org.czw.exammanger.vo.AdminVo;
import org.czw.exammanger.util.JsonDto;

public interface AdminService {
  JsonDto alter(AdminVo adminQo);
  JsonDto initAdmin();
  JsonDto roles();
  JsonDto delAdmin(AdminVo adminQo);
  JsonDto alterAdmins(AdminVo adminQo);
  JsonDto addAdmin(AdminVo adminQo);
  JsonDto initExaminer();
  JsonDto examiners();
}
