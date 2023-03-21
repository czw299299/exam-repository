package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInf {
    @FieldUtile(desc = "角色ID",type ="input")
    private Integer rid;
    @FieldUtile(desc = "角色名称",type ="input")
    private String rname;
    @FieldUtile(desc = "角色状态",type ="select")
    private Integer rstate;
    private  String rctime;
    private String rmtime;
}
