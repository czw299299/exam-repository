package org.czw.examplay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.examplay.pojo.PaperInf;
import org.czw.examplay.pojo.SubjectInf;
import org.czw.examplay.pojo.TopicInf;


import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperVo {
    private PaperInf paper;
    private SubjectInf subject;
    private ArrayList<TopicInf> topics;
}
