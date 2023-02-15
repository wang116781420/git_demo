package com.javasm.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.javasm.commons.base.ServiceImpl;
import com.javasm.commons.util.TreeUtil;
import com.javasm.sys.dao.DeptDao;
import com.javasm.sys.entity.Dept;
import com.javasm.sys.entity.User;
import com.javasm.sys.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeptServiceImpl extends ServiceImpl<Dept, DeptDao> implements DeptService {
    private static final Logger l = LoggerFactory.getLogger(DeptServiceImpl.class);

    @Override
    public String generateId(String pid) {
        if(StrUtil.isEmpty(pid))return null;
        //查询obj.getPid()下的子部门最大id
        String id = this.baseDao.getLastIdByParentId(pid);
        String newId = null;
        if(id==null){
            return pid+"01";
        }else{
            newId = String.valueOf(Integer.parseInt(id)+1);
            if(newId.endsWith("00"))return null;
        }
        return newId;
    }

    @Override
    public List<User> getChargePerson(String deptId,String posId) {
        return this.baseDao.getChargePerson(deptId,posId);
    }

    @Override
    public boolean updatePersonById(String id,String posId) {
        return (this.baseDao.updatePersonById(id,posId)) >= 1;
    }

    @Override
    public List<Dept> getDeptTree(String dname,String status) {
        Collection<Dept> list = null;
        if (StringUtils.hasLength(dname) || StringUtils.hasLength(status))
            list = getListByDeptName(dname,status);
        else
            list = getList(null);

        return TreeUtil.buildTree(list, "-1");
    }

    private Collection<Dept> getListByDeptName(String dname,String status) {
        Collection<Dept> list = this.baseDao.getListByDname(dname,status);
        List<String> ids = list.stream().map(Dept::getId).collect(Collectors.toList());
        Set<String> allids = new HashSet<>();
        for (String id : ids) {
            int length = id.length();
            int s = length / 2;
            for (int i = 1; i < s; i++) {
                String parentid = id.substring(0, 2 * i);
                if(!ids.contains(parentid))
                    allids.add(parentid);
            }
        }
        Collection<Dept> parentDepts = null;
        if (allids.size()>0){
            parentDepts = getByIds(allids);
            parentDepts.addAll(list);
            return parentDepts;
        }
        return list;
    }

}
