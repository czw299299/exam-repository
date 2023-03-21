package org.czw.exammanger.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.pojo.PaperInf;
import org.czw.exammanger.pojo.SubjectInf;
import org.czw.exammanger.pojo.TopicInf;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperVo {
    private PaperInf paper;
    private SubjectInf subject;
    private ArrayList<TopicInf> topics;
}
