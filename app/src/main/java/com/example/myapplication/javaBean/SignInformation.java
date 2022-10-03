package com.example.myapplication.javaBean;

public class SignInformation {

    private int courseId;
    private String courseName;
    private String courseNum;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public String toString() {
        return "SignInformation{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseNum='" + courseNum + '\'' +
                '}';
    }
}
