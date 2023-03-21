package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperInf {
    private  Integer pid;
    private  Integer sid;
    private  String pname;
    private  String pctime;
    private  String pmtime;

}
