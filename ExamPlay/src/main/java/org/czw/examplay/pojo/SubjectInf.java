package org.czw.examplay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectInf {
      private  Integer sid;
      private  String sname;
      private  Integer sstate;
      private String  sctime;
      private  String smtime;
}
