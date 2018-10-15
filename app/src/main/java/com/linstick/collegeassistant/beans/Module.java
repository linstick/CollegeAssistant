package com.linstick.collegeassistant.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Module extends DataSupport implements Serializable {
    public final static String[] MODULE_LIST = new String[]{
            "企业宣讲会", "社团活动", "高校讲座", "体育活动", "校园生活", "其他活动"
    };

    private int id;
    private String name;
    private String simpleName;

    public Module() {
    }

    public Module(int id, String name, String simpleName) {
        this.id = id;
        this.name = name;
        this.simpleName = simpleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", simpleName='" + simpleName + '\'' +
                '}';
    }
}
