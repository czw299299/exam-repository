package org.czw.exammanger.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.pojo.SubjectInf;
import org.czw.exammanger.pojo.TopicInf;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicVo {
    private TopicInf topic;
    private SubjectInf subject;
    private  Integer sid;
    private  Integer eid;
    private Integer awid;
    private  Integer awstate;
    private  String awmtime;
    private  Integer pid;
}
