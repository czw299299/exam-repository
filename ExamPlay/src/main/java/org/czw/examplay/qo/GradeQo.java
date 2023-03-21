package org.czw.examplay.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.examplay.util.FieldUtile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeQo {
    @FieldUtile(desc = "考场号" ,type="input")
    private String eid;
    @FieldUtile(desc = "考场名" ,type="input")
    private String ename;
    @FieldUtile(desc = "准考证" ,type="input")
    private  Integer awid;
    @FieldUtile(desc = "考生名" ,type="input")
    private  String eename;
    @FieldUtile(desc = "得分" ,type="input")
    private  Integer grade;
    @FieldUtile(desc = "开考时间" ,type="input")
    private  String estime;
    @FieldUtile(desc = "结束时间" ,type="input")
    private  String eetime;
}
