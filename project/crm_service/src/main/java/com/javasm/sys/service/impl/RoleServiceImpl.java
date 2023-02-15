package com.javasm.sys.service.impl;

import com.javasm.commons.base.ServiceImpl;
import com.javasm.sys.dao.RoleDao;
import com.javasm.sys.entity.Role;
import com.javasm.sys.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<Role, RoleDao> implements RoleService {
    private static final Logger l = LoggerFactory.getLogger(RoleServiceImpl.class);

}
