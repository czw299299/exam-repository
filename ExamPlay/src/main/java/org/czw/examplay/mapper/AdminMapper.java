package org.czw.examplay.mapper;

import org.apache.ibatis.annotations.Mapper;

import org.czw.examplay.pojo.AdminInf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {
    AdminInf check(AdminInf adminInf);
    Integer alter(AdminInf adminInf);
    List<AdminInf> allAdmin();
    Integer delAdmin(Integer rid);
    Integer  addadmin(AdminInf adminInf);

    List<AdminInf>  allexaminer();
}
