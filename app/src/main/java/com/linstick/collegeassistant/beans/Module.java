package com.linstick.collegeassistant.beans;

import java.io.Serializable;

public class Module implements Serializable {
    public final static String[] MODULE_LIST = new String[]{
            "企业宣讲会", "社团活动", "高校讲座", "体育活动", "校园生活", "其他活动"
    };
    private int id;
    private String name;

    public Module() {
        this.id = (int) (Math.random() * 1000) % MODULE_LIST.length;
        this.name = Module.MODULE_LIST[id];
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

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
