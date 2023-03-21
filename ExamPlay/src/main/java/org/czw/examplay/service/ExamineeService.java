package org.czw.examplay.service;


import org.czw.examplay.pojo.ExamineeInf;
import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.ExamineeVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public interface ExamineeService {
    JsonDto alter(ExamineeVo examineeVo);
    JsonDto alterImg(ExamineeVo examineeVo);
    JsonDto  re(MultipartFile file,ExamineeInf examineeInf, String pwd2, String vode, HttpSession session) ;
    JsonDto  addexaminee(MultipartFile file,ExamineeInf examineeInf, String pwd2, String vode, HttpSession session);
    JsonDto  showall(ExamineeVo examineeVo);
    JsonDto  selecteeid(ExamineeVo examineeVo);
    JsonDto   upexaminee(MultipartFile file, HttpSession session,ExamineeInf examineeInf);
    JsonDto   upexaminee2(HttpSession session,ExamineeInf examineeInf,String eepwd2);
    JsonDto  imgUpload(MultipartFile file, HttpSession session) throws IOException;
}
