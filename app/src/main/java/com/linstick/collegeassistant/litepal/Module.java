package com.linstick.collegeassistant.litepal;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 模块表
 */
public class Module extends DataSupport implements Serializable {
    private int id;
    private String name;
    private String simpleName;

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
