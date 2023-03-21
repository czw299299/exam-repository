package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectInf {
      @FieldUtile(desc ="科目ID",type = "input")
      private  Integer sid;
      @FieldUtile(desc ="科目ID",type = "input")
      private  String sname;
      @FieldUtile(desc ="状态",type = "select")
      private  Integer sstate;
      private String  sctime;
      private  String smtime;
}
