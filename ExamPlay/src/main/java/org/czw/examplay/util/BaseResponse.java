package org.czw.examplay.util;


import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaseResponse<T>{
    private List<Prop> FieldDesc;//[{prop:属性名，desc:描述，type：xxx},{}]
    private List<T> dataList;
    private Integer dataTotal;
    public BaseResponse(T t){

    }
    public BaseResponse(List<T> tList){
        this.dataList=tList;
        init();

    }
    private  void  init(){
        if (!dataList.isEmpty()){
            this.FieldDesc= new ArrayList<>();
            Class<T> tClass= (Class<T>) dataList.get(0).getClass();
            Field[] fields=tClass.getDeclaredFields();
            for (Field field: fields) {
                  if (field.isAnnotationPresent(FieldUtile.class)){
                       FieldUtile fieldUtile=field.getAnnotation(FieldUtile.class);
                      Prop prop=new Prop(field.getName(),fieldUtile.desc(),fieldUtile.type());
                       this.FieldDesc.add(prop);
                  }
            }

        }
    }
}
