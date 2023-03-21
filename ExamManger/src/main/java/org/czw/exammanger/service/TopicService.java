package org.czw.exammanger.service;



import org.czw.exammanger.pojo.TopicInf;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.TopicVo;

import java.util.ArrayList;

public interface TopicService {
    JsonDto showtopic(TopicVo topicvo);
    JsonDto  addtopic(TopicInf topicInf);
    JsonDto  deltopic(Integer tid);
    JsonDto  uptopic(TopicInf topicInf);
    JsonDto  subjects();
    JsonDto topicSo(TopicVo topicVo);

}
