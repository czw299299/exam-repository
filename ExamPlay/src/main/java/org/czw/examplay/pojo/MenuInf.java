package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInf {
    private  Integer mid;
    private  String mname;
    private  String murl;
    private  Integer  mpid;
    private  String mctime;
    private  String mmtime;
}
