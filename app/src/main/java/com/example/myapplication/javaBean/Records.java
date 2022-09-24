package com.example.myapplication.javaBean;

import java.util.List;

public class Records {
    private List<Course> records;

    public List<Course> getRecords() {
        return records;
    }

    public void setRecords(List<Course> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "records{" +
                "records=" + records +
                '}';
    }
}
