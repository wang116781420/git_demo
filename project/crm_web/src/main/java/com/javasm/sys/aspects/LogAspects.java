package com.javasm.sys.aspects;

import cn.hutool.core.date.DateUtil;
import com.javasm.commons.enums.OpsResult;
import com.javasm.sys.CurrentLogin;
import com.javasm.sys.annotations.OpsLog;
import com.javasm.sys.entity.Logs;
import com.javasm.sys.service.LogsService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.lang.reflect.Method;

//日志切面父容器;目标对象在子容器
@Controller
@Aspect
public class LogAspects {
    @Resource
    private LogsService ls;

    @Pointcut("@annotation(com.javasm.sys.annotations.OpsLog)")
    public void logpc(){}

    @Around("logpc()")
    public Object doLog(ProceedingJoinPoint jp){
        Object result =null;
        int a=1;
        Logs l = new Logs();
        try {

            l.setOpsUser(CurrentLogin.getLoginUserName());
            l.setOpsTime(DateUtil.now());
            l.setModelName(getModelName(jp.getTarget()));
            MethodSignature signature = (MethodSignature)jp.getSignature();
            l.setOpsType(getOps(jp.getTarget(),signature.getMethod()));
            result = jp.proceed();

            l.setOpsResult(OpsResult.OPS_SUC);
        } catch (Throwable throwable) {
            l.setOpsResult(OpsResult.OPS_ERROR);
            throw (RuntimeException)throwable;//一定要抛,不抛全局异常处理中接不到异常信息
        }finally {
            if(l.getOpsUser() == null) l.setOpsUser(CurrentLogin.getLoginUserName());
            if(l.getOpsUser() != null) ls.insert(l);
        }
        return result;
    }

    public String getOps(Object target, Method method){
        try {
            Method method1 = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            OpsLog annotation = method1.getAnnotation(OpsLog.class);
            return annotation.value();
        } catch (NoSuchMethodException e) {
            return "";
        }
    }

    public String getModelName(Object target){
        OpsLog annotation = target.getClass().getAnnotation(OpsLog.class);
        if(annotation==null)return "";
        return annotation.model();
    }

}
