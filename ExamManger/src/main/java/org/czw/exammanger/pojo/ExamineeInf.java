package org.czw.exammanger.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.util.FieldUtile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamineeInf {
    @FieldUtile(desc = "考生ID",type = "input")
    private  Integer eeid;
    @FieldUtile(desc = "角色",type = "select")
    private  Integer rid;
    @FieldUtile(desc = "考生账号",type = "input")
    private  String ic;
    @FieldUtile(desc = "考生照片",type = "img")
    private  String eeimg;
    @FieldUtile(desc = "考生名",type = "input")
    private  String eename;
    @FieldUtile(desc = "考生密码",type = "input")
    private  String eepwd;
    @FieldUtile(desc = "考生状态",type = "select")
    private  Integer eestate;

    private  String eectime;
    private  String eemtime;
}
