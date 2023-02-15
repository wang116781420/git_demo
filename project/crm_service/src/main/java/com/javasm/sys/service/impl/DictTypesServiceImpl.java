package com.javasm.sys.service.impl;

import com.javasm.commons.base.ServiceImpl;
import com.javasm.sys.dao.DictTypesDao;
import com.javasm.sys.entity.DictTypes;
import com.javasm.sys.service.DictTypesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DictTypesServiceImpl extends ServiceImpl<DictTypes, DictTypesDao> implements DictTypesService {
    private static final Logger l = LoggerFactory.getLogger(DictTypesServiceImpl.class);

}
