package com.javasm.sys;

import com.javasm.commons.base.BaseController;
import com.javasm.commons.entity.AxiosResult;
import com.javasm.commons.entity.TableDatas;
import com.javasm.commons.util.Gi;
import com.javasm.sys.entity.User;
import com.javasm.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    private static final Logger l = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService baseService;

    @GetMapping("{ids}")
    public AxiosResult getByIds(@PathVariable String ids) {
        String[] idsArray = ids.split(",");
        Collection<User> list = baseService.getByIds(Arrays.asList(idsArray));
        return suc(list);
    }

    @GetMapping
    public AxiosResult getList(User obj) {
        startPage();
        Collection<User> list = baseService.getList(obj);
        return suc(new TableDatas(list));
    }

    @PostMapping
    public AxiosResult insert(@RequestBody User obj) {
        obj.setId(Gi.snowid());
        obj.setCreateBy(CurrentLogin.getLoginUserName());
        obj.setUpdateBy(CurrentLogin.getLoginUserName());
        boolean b = baseService.insert(obj);
        return isok(b);
    }

    @PutMapping
    public AxiosResult updateById(@RequestBody User obj) {
        obj.setUpdateBy(CurrentLogin.getLoginUserName());
        boolean b = baseService.updateById(obj);
        return isok(b);
    }

    @DeleteMapping("{ids}")
    public AxiosResult delByIds(@PathVariable String ids) {
        boolean b = baseService.delByIds(Arrays.asList(ids.split(",")));
        return isok(b);
    }

}

