package com.javasm.sys.service.impl;

import com.javasm.commons.base.ServiceImpl;
import com.javasm.sys.dao.LogsDao;
import com.javasm.sys.entity.Logs;
import com.javasm.sys.service.LogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogsServiceImpl extends ServiceImpl<Logs, LogsDao> implements LogsService {
    private static final Logger l = LoggerFactory.getLogger(LogsServiceImpl.class);

}
