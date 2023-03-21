package org.czw.exammanger.service;

import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.MenuVo;
import org.czw.exammanger.vo.RoleVo;

public interface RoleService {
    JsonDto initRole();
    JsonDto allpowers();
    JsonDto ownpowers(MenuVo menuVo);
    JsonDto alterpowers(RoleVo roleVo);
    JsonDto alterRole(RoleVo roleVo);
    JsonDto addRole(RoleVo roleVo);
    JsonDto delRole(RoleVo roleVo);
}
