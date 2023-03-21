package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicInf {
        @FieldUtile(desc = "题目ID",type ="input")
        private Integer tid;
        @FieldUtile(desc = "科目",type ="select")
        private  Integer sid;
        @FieldUtile(desc = "题目内容",type ="input")
        private  String tcontent;
        @FieldUtile(desc = "题目答案",type ="checkList")
        private  String isAsr;
        @FieldUtile(desc = "答案A",type ="input")
        private  String optionA;
        @FieldUtile(desc = "答案B",type ="input")
        private  String optionB;
        @FieldUtile(desc = "答案C",type ="input")
        private  String optionC;
        @FieldUtile(desc = "答案D",type ="input")
        private  String optionD;
        @FieldUtile(desc = "答案E",type ="input")
        private  String optionE;
}
