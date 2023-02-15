package com.javasm.sys;

import com.javasm.commons.base.BaseController;
import com.javasm.commons.entity.AxiosResult;
import com.javasm.commons.entity.TableDatas;
import com.javasm.sys.entity.Logs;
import com.javasm.sys.service.LogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("logs")
public class LogsController extends BaseController {
    private static final Logger l = LoggerFactory.getLogger(LogsController.class);

    @Resource
    private LogsService baseService;

    @GetMapping("{ids}")
    public AxiosResult getByIds(@PathVariable String ids) {
        String[] idsArray = ids.split(",");
        Collection<Logs> list = baseService.getByIds(Arrays.asList(idsArray));
        return suc(list);
    }

    @GetMapping
    public AxiosResult getList(Logs obj) {
        startPage();
        Collection<Logs> list = baseService.getList(obj);
        return suc(new TableDatas(list));
    }

    @PostMapping
    public AxiosResult insert(@RequestBody Logs obj) {
        boolean b = baseService.insert(obj);
        return isok(b);
    }

    @PutMapping
    public AxiosResult updateById(@RequestBody Logs obj) {
        boolean b = baseService.updateById(obj);
        return isok(b);
    }

    @DeleteMapping("{ids}")
    public AxiosResult delByIds(@PathVariable String ids) {
        boolean b = baseService.delByIds(Arrays.asList(ids.split(",")));
        return isok(b);
    }

}

