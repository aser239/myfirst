package com.example.myapplication.javaBean;

import androidx.annotation.NonNull;

public class Person {
    private String userName;
    private String password;
    private String id;
    private String appKey;
    private int roleId;
    private boolean gender;
    private String realName;
    private String idNumber;
    private String collegeName;
    private String phone;
    private String email;
    private String avatar;
    private int inSchoolTime;
    private String createTime;
    private String lastUpdateTime;

    public Person(){}

    public void setUsername(String username){
        this.userName = username;
    }
    public String getUsername(){
        return userName;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getInSchoolTime() {
        return inSchoolTime;
    }

    public void setInSchoolTime(int inSchoolTime) {
        this.inSchoolTime = inSchoolTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", appKey='" + appKey + '\'' +
                ", roleId=" + roleId +
                ", gender=" + gender +
                ", realName='" + realName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", inSchoolTime='" + inSchoolTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}
