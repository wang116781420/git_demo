package com.javasm.commons.base;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface IMapper<T> {
    T getById(String id);
    List<T> getByIds(@Param("ids") Collection<String> ids);
    List<T> getList(T obj);

    int insert(T obj);
    int updateById(T obj);
    int delById(String id);
    int delByIds(@Param("ids")Collection<String> ids);
}
