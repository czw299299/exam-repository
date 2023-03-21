package org.czw.exammanger.service.Impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import org.czw.exammanger.mapper.TopicMapper;
import org.czw.exammanger.pojo.TopicInf;
import org.czw.exammanger.service.ImportTemplateService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.TopicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ImportTemplateServiceImpl implements ImportTemplateService {
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public JsonDto importTemplate(MultipartFile file) {
        JsonDto jsonDto = new JsonDto();
        HashMap<Object, Object> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            System.out.println("input"+inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0); //指定输入流和sheet
        // 读取第二行到最后一行数据
        List<List<Object>> read = reader.read(1, reader.getRowCount());
        List<TopicInf> topicInfs = new ArrayList<>();
        for (List<Object> objects : read) {
            TopicInf topicInf = new TopicInf();
            Object sid= objects.get(0);//读取某行第一列数据
            Object tcontent = objects.get(1); //读取某行第二列数据
            Object isArr = objects.get(2); //读取某行第三列数据
             Object A= objects.get(3);//读取某行第四列数据
            Object B= objects.get(4);
            Object C= objects.get(5);
            Object D= objects.get(6);
            Object E= objects.get(7);

            topicInf.setSid(Integer.parseInt(sid.toString()));
            topicInf.setTcontent(tcontent.toString());
            topicInf.setIsAsr(isArr.toString());
            topicInf.setOptionA(A.toString());
            topicInf.setOptionB(B.toString());
            topicInf.setOptionC(C.toString());
            topicInf.setOptionD(D.toString());
            topicInf.setOptionE(E.toString());
            topicInfs.add(topicInf);

        }
        Integer addtopics = topicMapper.addtopics(topicInfs);
        if (addtopics!=0){
            TopicVo topicVo = new TopicVo();
            ArrayList<TopicInf> topicInfs1 = topicMapper.selectsidtopic(topicVo.getSubject());
            BaseResponse<TopicInf> baseResponse = new BaseResponse<>(topicInfs1);
            baseResponse.setDataTotal(topicInfs1.size());
            map.put("datas",baseResponse);
            jsonDto.setId(1);
            jsonDto.setMap(map);
            jsonDto.setMessage("批量添加成功");
        }else {
            jsonDto.setId(0);
            jsonDto.setMessage("批量添加失败");
        }
        return jsonDto;

    }
}

