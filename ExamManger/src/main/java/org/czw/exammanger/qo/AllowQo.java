package org.czw.exammanger.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowQo {
    @FieldUtile(desc = "准考证",type = "input")
    private Integer awid;
    @FieldUtile(desc = "考场号",type = "input")
    private String  eid;
    @FieldUtile(desc = "考试名",type = "input")
    private String  ename;
    @FieldUtile(desc = "考生头像",type = "img")
    private String  eeimg;
    @FieldUtile(desc = "考生名",type = "input")
    private String eename;
    @FieldUtile(desc = "开考时间",type = "date")
    private String estime;
    @FieldUtile(desc = "开考时间",type = "date")
    private String eetime;
    @FieldUtile(desc = "考生状态",type = "select")
    private  Integer awstate;
    @FieldUtile(desc = "考试状态",type = "select")
    private Integer  estate;
}
