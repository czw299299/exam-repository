package org.czw.exammanger.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor//无参构造
@AllArgsConstructor//有参构造
@Data//getset方法
public class JsonDto<T>{
    private Integer id; // 这里的id判断成功1或者失败0
    private String message; // 这里的message是发送消息
    private String token;//保存token数据
    private Map<String, Object> map = new HashMap<>();//保存数据
}
