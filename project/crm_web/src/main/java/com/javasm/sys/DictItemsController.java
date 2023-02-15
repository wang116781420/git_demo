package com.javasm.sys;

import com.javasm.commons.base.BaseController;
import com.javasm.commons.entity.AxiosResult;
import com.javasm.commons.entity.TableDatas;
import com.javasm.commons.util.Gi;
import com.javasm.sys.entity.DictItems;
import com.javasm.sys.service.DictItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("dictItems")
public class DictItemsController extends BaseController {
    private static final Logger l = LoggerFactory.getLogger(DictItemsController.class);

    @Resource
    private DictItemsService baseService;

    @GetMapping("items/{dictType}")
    public AxiosResult getItemsByType(@PathVariable String dictType) {
        DictItems items = new DictItems();
        items.setDictType(dictType);
        Collection<DictItems> list = baseService.getList(items);
        return suc(list);
    }

    @GetMapping("{ids}")
    public AxiosResult getByIds(@PathVariable String ids) {
        String[] idsArray = ids.split(",");
        Collection<DictItems> list = baseService.getByIds(Arrays.asList(idsArray));
        return suc(list);
    }

    @GetMapping
    public AxiosResult getList(DictItems obj) {
        startPage();
        Collection<DictItems> list = baseService.getList(obj);
        return suc(new TableDatas(list));
    }

    @PostMapping
    public AxiosResult insert(@RequestBody DictItems obj) {
        obj.setId(Gi.snowid());
        boolean b = baseService.insert(obj);
        return isok(b);
    }

    @PutMapping
    public AxiosResult updateById(@RequestBody DictItems obj) {
        boolean b = baseService.updateById(obj);
        return isok(b);
    }

    @DeleteMapping("{ids}")
    public AxiosResult delByIds(@PathVariable String ids) {
        boolean b = baseService.delByIds(Arrays.asList(ids.split(",")));
        return isok(b);
    }

}

