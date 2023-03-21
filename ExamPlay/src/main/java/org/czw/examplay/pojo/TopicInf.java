package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicInf {
        private Integer tid;
        private  Integer sid;
        private  String tcontent;
        private  String isAsr;
        private  String optionA;
        private  String optionB;
        private  String optionC;
        private  String optionD;
        private  String optionE;
}
