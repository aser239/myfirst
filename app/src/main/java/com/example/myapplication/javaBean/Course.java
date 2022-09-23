package com.example.myapplication.javaBean;

import java.util.Arrays;

public class Course {
    private int current;
    private Arrays[] records;
    private int size;
    private int total;
    private String courseName;
    private String coursePhoto;
    private String collegeName;
    private int courseId;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public Arrays[] getRecords() {
        return records;
    }

    public void setRecords(Arrays[] records) {
        this.records = records;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
                "current=" + current +
                ", records=" + Arrays.toString(records) +
                ", size=" + size +
                ", total=" + total +
                ", courseName='" + courseName + '\'' +
                ", coursePhoto='" + coursePhoto + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
