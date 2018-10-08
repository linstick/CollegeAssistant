package com.linstick.collegeassistant.beans;

public class Module {
    private int id;
    private String name;

    public Module() {
        this.name = "社团活动";
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
}
