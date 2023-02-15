package com.javasm.sys.dao;

import com.javasm.commons.base.IMapper;
import com.javasm.sys.entity.Dept;
import com.javasm.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface DeptDao extends IMapper<Dept> {

    Collection<Dept> getListByDname(@Param("dname")String dname,@Param("status")String status);

    String getLastIdByParentId(String pid);

    List<User> getChargePerson(@Param("deptId") String deptId,@Param("posId") String posId);

    int updatePersonById(@Param("id") String id,@Param("posId") String posId);
}

