package org.czw.exammanger.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.pojo.ExamineeInf;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamineeVo {
   private ExamineeInf examinee;
   private   List<ExamineeInf>         examinees;
}
