package com.example.myapplication.javaBean;

public class CourseDetail {
    private String collegeName;
    private String courseName;
    private String coursePhoto;
    private long createTime;
    private long endTime;
    private boolean hasSelect;
    private int id;
    private String introduce;
    private String realName;
    private long startTime;
    private String userName;


    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePhoto() {
        return coursePhoto;
    }

    public void setCoursePhoto(String coursePhoto) {
        this.coursePhoto = coursePhoto;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CourseDetail{" +
                "collegeName='" + collegeName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", coursePhoto='" + coursePhoto + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", hasSelect=" + hasSelect +
                ", id=" + id +
                ", introduce='" + introduce + '\'' +
                ", realName='" + realName + '\'' +
                ", startTime=" + startTime +
                ", userName='" + userName + '\'' +
                '}';
    }
}
