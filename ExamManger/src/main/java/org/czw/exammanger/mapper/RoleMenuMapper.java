package org.czw.exammanger.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.czw.exammanger.pojo.RoleInf;
import org.czw.exammanger.pojo.RoleMenuInf;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface RoleMenuMapper {
    Integer addrolemenu(@Param(value = "list") List<RoleMenuInf> list);
    Integer delrolemenu(@Param(value = "list") List<RoleMenuInf> list);
    Integer delrole(RoleInf roleInf);
}
