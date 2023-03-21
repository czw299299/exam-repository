package org.czw.examplay.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamQo {
    private Integer awid;
    private Integer eid;
    private String sname;
    private String aacc;
    private String pname;
    private Integer sid;
    private Integer aid;
    private Integer pid;
    private String estime;
    private String eetime;
    private  Integer awstate;
    private Integer estate;
    private String ectime;
    private String emtime;
}
