package com.javasm.sys.service.impl;

import com.javasm.commons.base.ServiceImpl;
import com.javasm.sys.dao.DictItemsDao;
import com.javasm.sys.entity.DictItems;
import com.javasm.sys.service.DictItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DictItemsServiceImpl extends ServiceImpl<DictItems, DictItemsDao> implements DictItemsService {
    private static final Logger l = LoggerFactory.getLogger(DictItemsServiceImpl.class);

}
