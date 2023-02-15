package com.javasm.exceptions;

import com.javasm.commons.entity.AxiosResult;

import com.javasm.sys.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@RestControllerAdvice//控制器增强注解
public class GlobExceptionHandler {
    private static Logger l = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(ArithmeticException.class)
    public AxiosResult doArithmeticException(ArithmeticException e){
        l.error(e.getMessage(),e);
        return AxiosResult.error(e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public AxiosResult doFileNotFoundException(FileNotFoundException e){
        l.error(e.getMessage(),e);
        return AxiosResult.error(e.getMessage());
    }

    @ExceptionHandler(MvcException.class)
    public AxiosResult doMvcException(MvcException e){
        l.error(e.getMessage(),e);
        return AxiosResult.error(e.getE());
    }

    //异常处理方法
    @ExceptionHandler(Exception.class)
    public AxiosResult doException(Exception e){
        l.error(e.getMessage(),e);
        return AxiosResult.error(e.getMessage());
    }
}
