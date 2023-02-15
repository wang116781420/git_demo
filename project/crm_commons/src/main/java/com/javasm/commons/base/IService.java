package com.javasm.commons.base;

import java.util.Collection;

public interface IService<T> {
    T getById(String id);
    Collection<T> getByIds(Collection<String> ids);
    Collection<T> getList(T obj);

    boolean insert(T obj);
    boolean updateById(T obj);
    boolean delById(String id);
    boolean delByIds(Collection<String> ids);
}
