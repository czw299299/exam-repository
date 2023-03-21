package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamInf {
    @FieldUtile(desc = "考试ID",type = "input")
    private Integer eid;
    @FieldUtile(desc = "考试科目",type = "select")
    private Integer sid;
    @FieldUtile(desc = "考官",type = "select")
    private Integer aid;
    @FieldUtile(desc = "考卷",type = "select")
    private Integer pid;
    @FieldUtile(desc = "考场",type = "input")
    private String ename;
    @FieldUtile(desc = "开考时间",type = "date")
    private String estime;
    @FieldUtile(desc = "结束时间",type = "date")
    private String eetime;
    @FieldUtile(desc = "考试状态",type = "select")
    private Integer estate;
    private String ectime;
    private String emtime;
}
