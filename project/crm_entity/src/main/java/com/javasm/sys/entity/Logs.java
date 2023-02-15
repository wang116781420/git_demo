package com.javasm.sys.entity;


import java.io.Serializable;

public class Logs implements Serializable {
    private static final long serialVersionUID = -20981412543181227L;

    private Integer id;

    private String modelName;

    private String opsType;

    private String opsUser;

    private String opsTime;

    private String opsResult;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOpsType() {
        return opsType;
    }

    public void setOpsType(String opsType) {
        this.opsType = opsType;
    }

    public String getOpsUser() {
        return opsUser;
    }

    public void setOpsUser(String opsUser) {
        this.opsUser = opsUser;
    }

    public String getOpsTime() {
        return opsTime;
    }

    public void setOpsTime(String opsTime) {
        this.opsTime = opsTime;
    }

    public String getOpsResult() {
        return opsResult;
    }

    public void setOpsResult(String opsResult) {
        this.opsResult = opsResult;
    }

}

