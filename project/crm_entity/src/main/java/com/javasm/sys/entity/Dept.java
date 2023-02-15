package com.javasm.sys.entity;


import java.io.Serializable;
import java.util.List;

public class Dept implements Serializable {
    private static final long serialVersionUID = -66656681236563183L;

    private String id;

    private String dname;

    private String daddress;

    private String createTime;

    private String updateTime;

    private String pid;

    private String uid;

    private String status;

    private List<Dept> childs;//自聚合,子部门集合

    public List<Dept> getChilds() {
        return childs;
    }

    public void setChilds(List<Dept> childs) {
        this.childs = childs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDaddress() {
        return daddress;
    }

    public void setDaddress(String daddress) {
        this.daddress = daddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

