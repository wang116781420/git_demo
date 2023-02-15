package com.javasm.commons.enums;

public enum E {
    SUC(20000,"成功"),
    ERROR(50000,"请求异常"),
    LOGINNMAE_NOT_EXISTS(50001,"用户名不存在" ),
    NOT_EXISTS(50002,"资源不存在" ),
    NO_LOGIN(50003,"未登录"),
    DISABLE(50004,"数据禁用"),
    PWD_ERROR(50005,"密码错误" ),
    ID_ERROR(50006,"ID超限" ),
    VALICODE_ERR(5007,"验证码错误");
    private Integer code;
    private String msg;

    E(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
