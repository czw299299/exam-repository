package org.czw.exammanger.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.czw.exammanger.pojo.AllowInf;
import org.czw.exammanger.qo.AllowQo;
import org.czw.exammanger.vo.AllowVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface AllowMapper {
    ArrayList<AllowQo>  showallowonline(AllowVo allowVo);
    ArrayList<AllowQo>  joinallowonline(AllowVo allowVo);
    Integer  alterAllow(AllowVo allowVo);
}
