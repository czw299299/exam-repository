package org.czw.examplay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamVo {
    private Integer eeid;
    private Integer aid;
    private Integer eid;
    private Integer sid;
    private Integer estate;
    private String  estime;
    private String  eetime;
}
