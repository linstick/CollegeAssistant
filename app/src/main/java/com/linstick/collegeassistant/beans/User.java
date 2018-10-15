package com.linstick.collegeassistant.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
public class User extends DataSupport implements Serializable {

    private int id;
    private int iconUrl;
    private String nickName;
    private String realName;
    private int age;
    private String university;
    private String klazz;
    private String department;
    private String major;
    private String address;
    private String cellNumber;
    private String email;
    private String password;
    private boolean isMale;
    private String description;
    private Date registerTime;

    public User() {
    }

    public User(int iconUrl, String nickName, String realName, int age, String university, String klazz, String department, String major, String address, String cellNumber, String email, String password, boolean isMale, String description, Date registerTime) {
        this.iconUrl = iconUrl;
        this.nickName = nickName;
        this.realName = realName;
        this.age = age;
        this.university = university;
        this.klazz = klazz;
        this.department = department;
        this.major = major;
        this.address = address;
        this.cellNumber = cellNumber;
        this.email = email;
        this.password = password;
        this.isMale = isMale;
        this.description = description;
        this.registerTime = registerTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(int iconUrl) {
        this.iconUrl = iconUrl;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", iconUrl='" + iconUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", age=" + age +
                ", university='" + university + '\'' +
                ", klazz='" + klazz + '\'' +
                ", department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", address='" + address + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isMale=" + isMale +
                ", description='" + description + '\'' +
                ", registerTime=" + registerTime +
                '}';
    }
}
