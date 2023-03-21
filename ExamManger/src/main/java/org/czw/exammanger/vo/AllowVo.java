package org.czw.exammanger.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.qo.AllowQo;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowVo {
    private Integer awid;
    private Integer eid;
    private Integer eeid;
    private String ename;
    private String eename;
    private  Integer estate;
    private  Integer awstate;
    private  String awctime;
    private  String awmtime;
    private ArrayList<AllowQo> allowQos;
}
