package org.czw.examplay.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.czw.examplay.pojo.RoleInf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    RoleInf loginRole(Integer rid);
    List<RoleInf> roles();
    Integer addRole(RoleInf role);
    Integer alterRole(RoleInf role);
    Integer delRole(RoleInf roleInf);
}
