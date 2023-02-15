package com.javasm.sys.service;

import com.javasm.commons.base.IService;
import com.javasm.sys.entity.User;

public interface UserService extends IService<User> {

    User getUserByName(String uname);

    void sendValicode(String uphone, String code);
}
