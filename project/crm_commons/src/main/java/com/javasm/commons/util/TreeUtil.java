package com.javasm.commons.util;

import com.javasm.sys.entity.Dept;
import com.javasm.sys.entity.Menus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TreeUtil {

    public static List<Dept> buildTree(Collection<Dept> list, String pid) {
        List<Dept> r = new ArrayList<>();
        for (Dept dept : list) {
            if(pid.equals(dept.getPid())){
                String id = dept.getId();
                List<Dept> childs = buildTree(list, id);
                if(childs!=null && childs.size()>0){
                    dept.setChilds(childs);
                }
                r.add(dept);
            }
        }
        return r;
    }

    public static List<Menus> buildMenuTree(Collection<Menus> list, String pid) {
        List<Menus> r = new ArrayList<>();
        for (Menus menus : list) {
            if (pid.equals(menus.getPid())) {
                String id = menus.getId();
                List<Menus> childMenus = buildMenuTree(list, id);
                if (childMenus != null && childMenus.size() > 0) {
                    menus.setChildMenus(childMenus);
                }
                r.add(menus);
            }
        }
        return r;
    }

}


