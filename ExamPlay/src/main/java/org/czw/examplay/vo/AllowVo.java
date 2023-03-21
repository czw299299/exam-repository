package org.czw.examplay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowVo {
    private Integer awid;
    private Integer eid;
    private Integer eeid;
    private String ename;
    private  Integer estate;
    private  Integer awstate;
    private  String awctime;
    private  String awmtime;
}
