package com.javasm.sys.service;

import com.javasm.commons.base.IService;
import com.javasm.sys.entity.Dept;
import com.javasm.sys.entity.User;

import java.util.List;

public interface DeptService extends IService<Dept> {

    List<Dept> getDeptTree(String dname,String status);

    String generateId(String pid);

    List<User> getChargePerson(String deptId,String posId);

    boolean updatePersonById(String id,String posId);
}
