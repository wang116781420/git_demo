package com.javasm.commons.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class ServiceImpl<T,D extends IMapper<T>> implements IService<T>{
    //Autowired比Resource强大,支持按照泛型注入
    @Autowired
    protected D baseDao;

    @Override
    public T getById(String id) {
        return baseDao.getById(id);
    }

    @Override
    public Collection<T> getByIds(Collection<String> ids) {
        return baseDao.getByIds(ids);
    }

    @Override
    public Collection<T> getList(T obj) {
        return baseDao.getList(obj);
    }

    @Override
    public boolean insert(T obj) {
        int r = baseDao.insert(obj);
        return r==1;
    }

    @Override
    public boolean updateById(T obj) {
        int i = baseDao.updateById(obj);
        return i==1;
    }

    @Override
    public boolean delById(String id) {
        int i = baseDao.delById(id);
        return i==1;
    }

    @Override
    public boolean delByIds(Collection<String> ids) {
        int i = baseDao.delByIds(ids);
        return i>=ids.size();
    }
}
