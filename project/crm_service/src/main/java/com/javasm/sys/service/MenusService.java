package com.javasm.sys.service;

import com.javasm.commons.base.IService;
import com.javasm.sys.entity.Icon;
import com.javasm.sys.entity.Menus;

import java.util.List;
import java.util.Map;

public interface MenusService extends IService<Menus> {

    Map<String, Object> getMenusTree(Menus m);
    String generateId(String pid);

}
