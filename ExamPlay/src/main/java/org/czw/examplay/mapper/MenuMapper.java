package org.czw.examplay.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.czw.examplay.pojo.MenuInf;
import org.czw.examplay.vo.MenuVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface MenuMapper {
    ArrayList<MenuInf> menushow(@Param("rid") int rid, @Param("mpid") int mpid);
    ArrayList<MenuInf> ownpowers(MenuVo menuVo);
}
