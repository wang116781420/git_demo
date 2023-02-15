package com.javasm.sys;

import com.javasm.sys.annotations.OpsLog;
import com.javasm.commons.base.BaseController;
import com.javasm.commons.entity.AxiosResult;
import com.javasm.commons.entity.TableDatas;
import com.javasm.commons.enums.E;
import com.javasm.exceptions.MvcException;
import com.javasm.sys.entity.Dept;
import com.javasm.sys.entity.User;
import com.javasm.sys.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("dept")
@OpsLog(model = "部门管理")
public class DeptController extends BaseController {
    private static final Logger l = LoggerFactory.getLogger(DeptController.class);

    @Resource
    private DeptService baseService;

    @GetMapping("tree")
    public AxiosResult buildDeptTree(String dname,String status){
        List<Dept> trees =baseService.getDeptTree(dname,status);
        return suc(trees);
    }

    @GetMapping("{ids}")
    public AxiosResult getByIds(@PathVariable String ids) {
        String[] idsArray = ids.split(",");
        Collection<Dept> list = baseService.getByIds(Arrays.asList(idsArray));
        if (list.isEmpty()) return new AxiosResult(E.ERROR);
        return suc(list);
    }

    @GetMapping
    public AxiosResult getList(Dept obj) {
        startPage();
        Collection<Dept> list = baseService.getList(obj);
        return suc(new TableDatas(list));
    }

    @PostMapping
    @OpsLog("添加部门")
    public AxiosResult insert(@RequestBody Dept obj) {
        String id=baseService.generateId(obj.getPid());
        if(id==null) throw new MvcException(E.ID_ERROR);
        obj.setId(id);
        boolean b = baseService.insert(obj);
        return isok(b);
    }

    @PutMapping
    @OpsLog("修改部门")
    public AxiosResult updateById(@RequestBody Dept obj) {
        boolean b = baseService.updateById(obj);
        return isok(b);
    }

    @DeleteMapping("{ids}")
    public AxiosResult delByIds(@PathVariable String ids) {
        boolean b = baseService.delByIds(Arrays.asList(ids.split(",")));
        return isok(b);
    }

    @GetMapping("getChargePerson")
    public AxiosResult getChargePerson(String deptId,String posId){
        List<User> chargePersons =baseService.getChargePerson(deptId,posId);
        return suc(chargePersons);
    }

    @PutMapping("{id}")
    public AxiosResult updatePersonById(@PathVariable String id,String posId){
        boolean b = baseService.updatePersonById(id,posId);
        return isok(b);
    }

}

