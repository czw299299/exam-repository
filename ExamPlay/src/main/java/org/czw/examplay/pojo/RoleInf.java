package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInf {
    private Integer rid;
    private String rname;
    private Integer rstate;
    private  String rctime;
    private String rmtime;
}
