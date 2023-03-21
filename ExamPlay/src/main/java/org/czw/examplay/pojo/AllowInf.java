package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowInf {
    private Integer awid;
    private Integer eid;
    private Integer eeid;
    private  Integer awstate;
    private  String awctime;
    private  String awmtime;
}
