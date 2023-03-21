package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeInf {
    private Integer gid;
    private Integer awid;
    private Integer grade;
    private String gctime;
    private String gmtime;

}
