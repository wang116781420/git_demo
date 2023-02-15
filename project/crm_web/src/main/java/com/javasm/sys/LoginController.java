package com.javasm.sys;

import cn.hutool.core.util.StrUtil;
import com.javasm.commons.base.BaseController;
import com.javasm.commons.cache.CacheService;
import com.javasm.commons.entity.AppProperties;
import com.javasm.commons.entity.AxiosResult;
import com.javasm.commons.enums.E;
import com.javasm.commons.enums.Flags;
import com.javasm.exceptions.MvcException;
import com.javasm.commons.util.JwtUtil;
import com.javasm.commons.util.Keys;
import com.javasm.commons.util.StringUtil;
import com.javasm.sys.annotations.OpsLog;
import com.javasm.sys.entity.User;
import com.javasm.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController extends BaseController {
    private static final Logger l = LoggerFactory.getLogger(DeptController.class);
    @Resource
    private UserService us;
    @Resource
    private CacheService cs;

    @GetMapping("valicode/{uphone}")
    public AxiosResult sendValicode(@PathVariable String uphone){
        String code = StringUtil.randomValicode();
        us.sendValicode(uphone,code);
        return AxiosResult.suc();
    }
    @GetMapping("loginByPhone/{uphone}/{code}")
    public AxiosResult loginByPhone(@PathVariable String uphone,@PathVariable String code){
        String byKey = (String) cs.get(Keys.VALICODE + uphone);
        if (StrUtil.isEmpty(byKey))throw new MvcException(E.VALICODE_ERR);
        if (!byKey.equals(code))throw new MvcException(E.VALICODE_ERR);

        cs.del(Keys.VALICODE+uphone);

        return AxiosResult.suc();
    }


    @PostMapping("loginByPwd")
    @OpsLog("登录")
    public ResponseEntity dologinByPwd(@RequestBody User u) {
        User t = us.getUserByName(u.getUname());
        if(t==null)throw new MvcException(E.NOT_EXISTS);
        CurrentLogin.setLoginUserName(t.getUname());
        if(t.getFlag().equals(Flags.disable)) throw new MvcException(E.DISABLE);
        if(!u.getUpwd().equals(t.getUpwd())) throw new MvcException(E.PWD_ERROR);
        t.setUpwd("");
        String jwt = JwtUtil.createJwt(t.getUname(), AppProperties.getLoginToken().getKey(), AppProperties.getLoginToken().getExpMinutes());
        cs.set(Keys.LOGIN_TOKEN+jwt,t.getUname(), ( AppProperties.getLoginToken().getExpMinutes()+2), TimeUnit.MINUTES);

        HttpHeaders headers=new HttpHeaders();
        headers.add("admin_token",jwt);
        return new ResponseEntity(suc(t),headers, HttpStatus.OK);
    }

    @GetMapping("logout")
    public AxiosResult dologout() {
        CurrentLogin.removeLoginUser();
        return suc();
    }
}
