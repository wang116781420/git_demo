package com.javasm.sys;

import com.javasm.commons.util.SpringUtil;
import com.javasm.sys.entity.User;
import com.javasm.sys.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CurrentLogin {

    private static ThreadLocal<String> LOGIN_USER_NAME=new ThreadLocal<>();
    private static UserService us;

    public static void removeLoginUser() {
        LOGIN_USER_NAME.remove();
    }

    public static void setLoginUserName(String uname){LOGIN_USER_NAME.set(uname);}

    public static String getLoginUserName(){return LOGIN_USER_NAME.get();}

    public static User getLoginUser() {
        if (us == null) us = SpringUtil.getBean(UserService.class);
        String loginUserName = getLoginUserName();
        Object obj = us.getUserByName(loginUserName);
        if (obj != null) return (User) obj;
        else {
            User t = new User();
            t.setUname("admin");
            return t;
        }
    }



}
