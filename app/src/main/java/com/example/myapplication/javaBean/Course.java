package com.example.myapplication.javaBean;

import java.util.Arrays;

public class Course {
    private String courseName;
    private String coursePhoto;
    private String collegeName;
    private int courseId;

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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", coursePhoto='" + coursePhoto + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}


