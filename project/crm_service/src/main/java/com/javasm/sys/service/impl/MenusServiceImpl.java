package com.javasm.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.javasm.commons.base.ServiceImpl;
import com.javasm.sys.CurrentLogin;
import com.javasm.commons.util.TreeUtil;
import com.javasm.sys.dao.MenusDao;
import com.javasm.sys.entity.Icon;
import com.javasm.sys.entity.Menus;
import com.javasm.sys.service.MenusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenusServiceImpl extends ServiceImpl<Menus, MenusDao> implements MenusService {
    private static final Logger l = LoggerFactory.getLogger(MenusServiceImpl.class);

    @Override
    public Map<String, Object> getMenusTree(Menus m) {
        Collection<Menus> list = getList(m);
        List<Menus> menus = TreeUtil.buildMenuTree(list, "-1");
        String loginUserName = CurrentLogin.getLoginUserName();
        Map<String,Object> map=new HashMap<>();
        map.put("menus",menus);
        map.put("loginUserName",loginUserName);
        return map;
    }
    @Override
    public String generateId(String pid) {
        if(StrUtil.isEmpty(pid))return null;
        String id = this.baseDao.getLastIdByParentId(pid);
        String newId = null;
        if(id==null){
            return pid+"01";
        }else{
            if (id.length()>=4){
            newId = String.valueOf(Integer.parseInt(id)+1);
            if(newId.endsWith("00"))return null;
            }else {
                newId= String.valueOf(Integer.parseInt(id)+10);
            }

        }
        return newId;
    }

}
