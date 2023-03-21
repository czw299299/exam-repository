package org.czw.exammanger.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.czw.exammanger.pojo.AdminInf;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVo {
    private AdminInf admin;
    private String code;
    private String apwd2;

}
