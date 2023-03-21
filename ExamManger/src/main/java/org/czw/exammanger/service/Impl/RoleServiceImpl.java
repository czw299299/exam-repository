package org.czw.exammanger.service.Impl;

import org.czw.exammanger.mapper.MenuMapper;
import org.czw.exammanger.mapper.RoleMapper;
import org.czw.exammanger.mapper.RoleMenuMapper;
import org.czw.exammanger.pojo.MenuInf;
import org.czw.exammanger.pojo.RoleInf;
import org.czw.exammanger.pojo.RoleMenuInf;
import org.czw.exammanger.service.RoleService;
import org.czw.exammanger.util.BaseResponse;
import org.czw.exammanger.util.JsonDto;
import org.czw.exammanger.vo.MenuVo;
import org.czw.exammanger.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private   RoleMapper roleMapper;
    @Autowired
    private MenuMapper  menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public JsonDto initRole() {
        List<RoleInf> roles = roleMapper.roles();
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        BaseResponse baseResponse = new BaseResponse<>(roles);
        baseResponse.setDataTotal(roles.size());
        map.put("datas",baseResponse);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        jsonDto.setMessage("进入角色管理");
        return jsonDto;
    }
    public  JsonDto  allpowers(){

        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ArrayList<MenuInf> menushow = menuMapper.menushow(1, 1);
        map.put("powers",menushow);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        jsonDto.setMessage("所有权限生成");
        return jsonDto;
    }

    @Override
    public JsonDto ownpowers(MenuVo menuVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        ArrayList<MenuInf> ownpowers = menuMapper.ownpowers(menuVo);
        map.put("ownpowers",ownpowers);
        jsonDto.setId(1);
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto alterpowers(RoleVo roleVo) {
        JsonDto jsonDto = new JsonDto();
        HashMap map = new HashMap();
        MenuVo menuVo = new MenuVo();
        menuVo.setRid(roleVo.getRole().getRid());
        menuVo.setMpid(1);
        ArrayList<RoleMenuInf> addroleMenuInfs = new ArrayList<>();
        ArrayList<RoleMenuInf> delroleMenuInfs = new ArrayList<>();
        ArrayList<MenuInf> ownpowers = menuMapper.ownpowers(menuVo);
        for (int i = 0; i <ownpowers.size(); i++) {
            Integer key=0;
            RoleMenuInf roleMenuInf = new RoleMenuInf();
            for (int j = 0; j <roleVo.getMenus().size() ; j++) {
                if (ownpowers.get(i).getMid()==roleVo.getMenus().get(j).getMid())
                {
                    key=1;
                }
            }
            if (key==0){
                roleMenuInf.setRid(roleVo.getRole().getRid());
                roleMenuInf.setMid(ownpowers.get(i).getMid());
                delroleMenuInfs.add(roleMenuInf);
            }
        }
        for (int i = 0; i <roleVo.getMenus().size(); i++) {
            Integer key=0;
            RoleMenuInf roleMenuInf = new RoleMenuInf();
            for (int j = 0; j <ownpowers.size() ; j++) {
                if (ownpowers.get(j).getMid()==roleVo.getMenus().get(i).getMid())
                {
                    key=1;
                }
            }
            if (key==0){
                roleMenuInf.setRid(roleVo.getRole().getRid());
                roleMenuInf.setMid(roleVo.getMenus().get(i).getMid());
                addroleMenuInfs.add(roleMenuInf);
            }
        }
        System.out.println("add"+addroleMenuInfs);
        System.out.println("del"+delroleMenuInfs);
        if (addroleMenuInfs.size()!=0){
            Integer integer = roleMenuMapper.addrolemenu(addroleMenuInfs);
            System.out.println("key"+integer);
        }

         if (delroleMenuInfs.size()!=0) {
             Integer integer1 = roleMenuMapper.delrolemenu(delroleMenuInfs);
         }
        jsonDto.setId(1);
        jsonDto.setMessage("权限修改成功");
        return jsonDto;
    }

    @Override
    public JsonDto alterRole(RoleVo roleVo) {
        Integer alterRole = roleMapper.alterRole(roleVo.getRole());
        JsonDto jsonDto = new JsonDto();
        jsonDto = alterpowers(roleVo);
        return jsonDto;
    }

    @Override
    public JsonDto addRole(RoleVo roleVo) {

        Integer integer = roleMapper.addRole(roleVo.getRole());
        System.out.println("rolekey"+integer);
        RoleMenuInf roleMenuInf = new RoleMenuInf();
        roleMenuInf.setRid(roleVo.getRole().getRid());
        roleMenuInf.setMid(1);
        ArrayList<RoleMenuInf> addroleMenuInfs = new ArrayList<>();
        addroleMenuInfs.add(roleMenuInf);
        Integer integer1 = roleMenuMapper.addrolemenu(addroleMenuInfs);
        System.out.println("key"+integer1);
        MenuInf menuInf = new MenuInf();
        menuInf.setMid(1);
        roleVo.getMenus().add(menuInf);
        JsonDto jsonDto = alterpowers(roleVo);
        HashMap map = new HashMap();
        map.put("role",roleVo.getRole());
        jsonDto.setMessage("添加成功");
        jsonDto.setMap(map);
        return jsonDto;
    }

    @Override
    public JsonDto delRole(RoleVo roleVo) {
        JsonDto jsonDto = new JsonDto();
        Integer integer = roleMapper.delRole(roleVo.getRole());
        if (integer!=0){
            Integer delrole = roleMenuMapper.delrole(roleVo.getRole());
            System.out.println("delrole"+delrole);
            jsonDto.setId(1);
            jsonDto.setMessage("删除成功");
        }
        return jsonDto;
    }
}
