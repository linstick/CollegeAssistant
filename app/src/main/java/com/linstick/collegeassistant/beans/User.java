package com.linstick.collegeassistant.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String nickName;
    private String realName;
    private int age;
    private String university;
    private String klazz;
    private String department;
    private String major;
    private String address;
    private String cellNumber;
    private String password;
    private boolean isMale;
    private String description;
    private Date registerTime;

    public User() {
        this.id = (int) (Math.random() * 10000);
        this.nickName = "用户" + (int) (Math.random() * 1000);
        this.realName = "罗瑞泳";
        this.age = 23;
        this.isMale = true;
        this.university = "广东工业大学";
        this.department = "计算机学院";
        this.major = "软件工程";
        this.klazz = "15级";
        this.description = "Android应用开发入门Coder";
        this.cellNumber = "15102032936";
        this.address = "广东省广州市番禺区小谷围街道广东工业大学西生活区";
        registerTime = new Date();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getKlazz() {
        return klazz;
    }

    public void setKlazz(String klazz) {
        this.klazz = klazz;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", age='" + age + '\'' +
                ", university='" + university + '\'' +
                ", klazz='" + klazz + '\'' +
                ", department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", address='" + address + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", password='" + password + '\'' +
                ", isMale=" + isMale +
                ", registerTime=" + registerTime +
                ", description=" + description +
                '}';
    }
}
