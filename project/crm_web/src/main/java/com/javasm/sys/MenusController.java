package com.javasm.sys;


import com.javasm.commons.base.BaseController;
import com.javasm.commons.entity.AxiosResult;
import com.javasm.commons.enums.E;
import com.javasm.exceptions.MvcException;
import com.javasm.sys.entity.Icon;
import com.javasm.sys.entity.Menus;
import com.javasm.sys.service.MenusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menus")
public class MenusController extends BaseController {
    private static final Logger l = LoggerFactory.getLogger(MenusController.class);

    @Resource
    private MenusService baseService;

    @GetMapping("{ids}")
    public AxiosResult getByIds(@PathVariable String ids) {
        String[] idsArray = ids.split(",");
        Collection<Menus> list = baseService.getByIds(Arrays.asList(idsArray));
        return suc(list);
    }

    @GetMapping("tree")
    public AxiosResult buildMenusTree(Menus m){
        Map<String, Object> map = baseService.getMenusTree(m);
        return suc(map);
    }

    @GetMapping
    public AxiosResult getList(Menus obj) {

        Collection<Menus> list = baseService.getList(obj);
        return suc(list);
    }

    @PostMapping
    public AxiosResult insert(@RequestBody Menus obj) {
        String id=baseService.generateId(obj.getPid());
        if(id==null) throw new MvcException(E.ID_ERROR);
        obj.setId(id);
        boolean b = baseService.insert(obj);
        return isok(b);
    }



    @PutMapping
    public AxiosResult updateById(@RequestBody Menus obj) {
        boolean b = baseService.updateById(obj);
        return isok(b);
    }

    @DeleteMapping("{ids}")
    public AxiosResult delByIds(@PathVariable String ids) {
        boolean b = baseService.delByIds(Arrays.asList(ids.split(",")));
        return isok(b);
    }
    @DeleteMapping
    public AxiosResult delById(String id){
        boolean b = baseService.delById(id);
        return isok(b);
    }

}

