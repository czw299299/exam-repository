package org.czw.examplay.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.czw.examplay.pojo.AllowInf;
import org.czw.examplay.qo.AllowQo;
import org.czw.examplay.vo.AllowVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface AllowMapper {
           ArrayList<AllowQo> selectallonline(AllowVo allowVo);
           ArrayList<AllowInf>  selectawid(@Param("awid") Integer awid);
           Integer addAllow(AllowInf allowInf);
           Integer delAllow(AllowVo allowVo);
           Integer alterAllow(AllowVo allowVo);
}
