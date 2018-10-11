package com.linstick.collegeassistant.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String nickName;
    private String realName;
    private String university;
    private String campusArea;
    private String klazz;
    private String department;
    private String major;
    private String address;
    private String cellNumber;
    private String password;
    private boolean isMale;
    private Date registerTime;

    public User() {
        this.nickName = "用户" + (int) (Math.random() * 1000);
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

    public String getCampusArea() {
        return campusArea;
    }

    public void setCampusArea(String campusArea) {
        this.campusArea = campusArea;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", university='" + university + '\'' +
                ", campusArea='" + campusArea + '\'' +
                ", klazz='" + klazz + '\'' +
                ", department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", address='" + address + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", password='" + password + '\'' +
                ", isMale=" + isMale +
                ", registerTime=" + registerTime +
                '}';
    }
}
