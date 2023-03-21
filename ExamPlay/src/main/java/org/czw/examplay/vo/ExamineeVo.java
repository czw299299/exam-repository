package org.czw.examplay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.examplay.pojo.ExamineeInf;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamineeVo {
     private ExamineeInf examinee;
     private String code;
     private String eepwd2;

}
