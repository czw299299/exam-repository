package org.czw.exammanger.service;

import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.PaperVo;
import org.czw.exammanger.vo.TopicVo;

import java.util.ArrayList;


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
