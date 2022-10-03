package com.example.myapplication.javaBean;

public class Records2Detail {
    private String courseName;
    private String courseAddr;
    private int createTime;
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

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUserSignId() {
        return userSignId;
    }

    public void setUserSignId(int userSignId) {
        this.userSignId = userSignId;
    }

    @Override
    public String toString() {
        return "Records2{" +
                "courseName='" + courseName + '\'' +
                ", courseAddr='" + courseAddr + '\'' +
                ", createTime=" + createTime +
                ", userSignId=" + userSignId +
                '}';
    }
}
