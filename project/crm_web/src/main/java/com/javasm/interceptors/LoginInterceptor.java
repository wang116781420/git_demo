package com.javasm.interceptors;

import cn.hutool.core.util.StrUtil;
import com.javasm.commons.cache.CacheService;
import com.javasm.commons.entity.AppProperties;
import com.javasm.commons.enums.E;
import com.javasm.commons.enums.Flags;
import com.javasm.exceptions.MvcException;
import com.javasm.sys.CurrentLogin;
import com.javasm.commons.util.JwtUtil;
import com.javasm.commons.util.Keys;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private CacheService cs;
    @Resource
    private AppProperties ap;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AppProperties.LoginToken loginToken = ap.getLoginToken();
        String key=loginToken.getKey();
        String t = request.getHeader("admin_token");
        if (StrUtil.isEmpty(t))throw new MvcException(E.NO_LOGIN);
        String s = JwtUtil.parseJws(t, key);
        if (s==null)throw new MvcException(E.NO_LOGIN);
        if (Flags.SignatureError.equals(s))throw new MvcException(E.NO_LOGIN);
        if (Flags.ExpiredJwtError.equals(s)) {              //过期
            s = (String) cs.get(Keys.LOGIN_TOKEN + t);
            if (StrUtil.isEmpty(s))throw new MvcException(E.NO_LOGIN);
            else {
                t = JwtUtil.createJwt(s,key, loginToken.getExpMinutes());
                response.addHeader("admin_token",t);
            }
        }
        String uname=s;
        CurrentLogin.setLoginUserName(uname);
        cs.set(Keys.LOGIN_TOKEN+t,s, (loginToken.getExpMinutes()+2), TimeUnit.MINUTES);
        return true;
    }
}
