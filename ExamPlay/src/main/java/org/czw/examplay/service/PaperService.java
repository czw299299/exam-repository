package org.czw.examplay.service;


import org.czw.examplay.util.JsonDto;
import org.czw.examplay.vo.PaperVo;

public interface PaperService {
    JsonDto showPaper(PaperVo paperVo);
    JsonDto addpaper(PaperVo paperVo);
    JsonDto alterpaper(PaperVo paperVo);
    JsonDto altertopics(PaperVo paperVo);
    JsonDto delpaper(Integer pid);
    JsonDto paperSo(PaperVo paperVo);
    JsonDto sidTopic(PaperVo paperVo);
    JsonDto ownTopics(PaperVo paperVo);
    JsonDto papers(PaperVo paperVo);
}
