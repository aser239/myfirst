package com.example.myapplication.javaBean;

import androidx.annotation.NonNull;

public class Records2Detail {
    private String courseName;
    private String courseAddr;
    private long createTime;
    private int userSignId;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseAddr() {
        return courseAddr;
    }

    public void setCourseAddr(String courseAddr) {
        this.courseAddr = courseAddr;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getUserSignId() {
        return userSignId;
    }

    public void setUserSignId(int userSignId) {
        this.userSignId = userSignId;
    }

    @NonNull
    @Override
    public String toString() {
        return "records{" +
                "courseName='" + courseName + '\'' +
                ", courseAddr='" + courseAddr + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userSignId='" + userSignId +'\''+
                '}';
    }
}
