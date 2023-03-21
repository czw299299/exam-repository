package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInf {
    private  Integer aid;
    private  String aacc;
    private  String apwd;
    private  Integer rid;
    private  Integer astate;
    private  String actime;
    private  String amtime;

}
