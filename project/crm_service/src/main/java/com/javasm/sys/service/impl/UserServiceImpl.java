package com.javasm.sys.service.impl;


import com.javasm.commons.base.ServiceImpl;
import com.javasm.commons.cache.CacheService;
import com.javasm.commons.util.Keys;
import com.javasm.sys.dao.UserDao;
import com.javasm.sys.entity.User;
import com.javasm.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<User, UserDao> implements UserService {
    private static final Logger l = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private CacheService cs;

    @Override
    public User getUserByName(String uname) {
     Object obj= cs.get(Keys.LOGINUSERS +uname);
      if (obj instanceof String && Keys.NOT_EXISTS.equals(obj))return null;

      if (obj!=null&&obj instanceof  User){
          return (User) obj;
      }

     User u = this.baseDao.getByName(uname);
      if (u!=null){
          cs.set(Keys.LOGINUSERS +uname, u,1, TimeUnit.HOURS);
      }else {
          cs.set(Keys.LOGINUSERS+uname,Keys.NOT_EXISTS,30, TimeUnit.SECONDS);
      }
        return u;
    }

    @Override
    public void sendValicode(String uphone, String code) {
        cs.set(Keys.VALICODE+uphone,code, 5, TimeUnit.MINUTES);
    }


}
