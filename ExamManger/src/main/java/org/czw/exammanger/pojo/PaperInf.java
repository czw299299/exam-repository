package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperInf {
    @FieldUtile( desc = "试卷ID",type = "input")
    private  Integer pid;
    @FieldUtile( desc = "科目",type = "select")
    private  Integer sid;
    @FieldUtile( desc = "试卷名",type = "input")
    private  String pname;

    private  String pctime;
    private  String pmtime;

}
