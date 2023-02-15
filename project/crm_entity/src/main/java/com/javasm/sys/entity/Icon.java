package com.javasm.sys.entity;

/**
 * @author:SG
 * @classname: Icon
 * @description:
 * @date: 2022/9/20 20:35
 * @version:0.1
 * @since:jdk11
 */
public class Icon {
    private String id;
    private String menuIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Icon(String id, String menuIcon) {
        this.id = id;
        this.menuIcon = menuIcon;
    }

    public Icon() {
    }
}
