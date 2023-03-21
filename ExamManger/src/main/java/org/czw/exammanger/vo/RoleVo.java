package org.czw.exammanger.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.pojo.MenuInf;
import org.czw.exammanger.pojo.RoleInf;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
   private RoleInf role;
   private ArrayList<MenuInf> menus;

}
