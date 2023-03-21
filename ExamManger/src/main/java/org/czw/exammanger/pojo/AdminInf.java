package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdminInf {
    @FieldUtile(desc="管理员id",type ="input")
    private  Integer aid;
    @FieldUtile(desc="管理员账号",type ="input")
    private  String aacc;
    @FieldUtile(desc="管理员密码",type ="input")
    private  String apwd;
    @FieldUtile(desc="管理员角色",type ="select")
    private  Integer rid;
    @FieldUtile(desc="管理员状态",type ="select")
    private  Integer astate;
    private  String actime;
    private  String amtime;

}
