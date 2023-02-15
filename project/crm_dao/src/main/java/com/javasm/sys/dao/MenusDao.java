package com.javasm.sys.dao;

import com.javasm.commons.base.IMapper;
import com.javasm.sys.entity.Icon;
import com.javasm.sys.entity.Menus;

import java.util.List;

public interface MenusDao extends IMapper<Menus> {

    String getLastIdByParentId(String pid);


}

