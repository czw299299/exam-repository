package org.czw.exammanger.service;



import org.czw.exammanger.pojo.SubjectInf;
import org.czw.exammanger.util.JsonDto;

import java.util.ArrayList;

public interface SubjectService {
      JsonDto subjects();
       JsonDto addsubject(SubjectInf subjectInf);
       JsonDto delsubject(Integer sid);
       JsonDto upsubject(SubjectInf subjectInf);
}
