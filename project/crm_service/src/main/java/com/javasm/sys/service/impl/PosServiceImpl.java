package com.javasm.sys.service.impl;

import com.javasm.commons.base.ServiceImpl;
import com.javasm.sys.dao.PosDao;
import com.javasm.sys.entity.Pos;
import com.javasm.sys.service.PosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PosServiceImpl extends ServiceImpl<Pos, PosDao> implements PosService {
    private static final Logger l = LoggerFactory.getLogger(PosServiceImpl.class);

}
