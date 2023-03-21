package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamineeInf {
    private  Integer eeid;
    private  Integer rid;
    private  String ic;
    private  String eeimg;
    private  String eename;
    private  String eepwd;
    private  Integer eestate;
    private  String eectime;
    private  String eemtime;
}
