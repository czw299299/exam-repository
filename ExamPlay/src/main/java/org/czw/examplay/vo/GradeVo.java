package org.czw.examplay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeVo {
    private Integer eeid;
    private Integer gid;
    private Integer awid;
    private Integer awstate;
    private String awmtime;
    private Integer grade;
    private String gctime;
    private String gmtime;
}
