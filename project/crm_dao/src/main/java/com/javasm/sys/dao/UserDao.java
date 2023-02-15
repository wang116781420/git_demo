package com.javasm.sys.dao;

import com.javasm.commons.base.IMapper;
import com.javasm.sys.entity.User;

public interface UserDao extends IMapper<User> {

    User getByName(String uname);
}

