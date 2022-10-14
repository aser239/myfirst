package com.example.myapplication.JavaBean;

import androidx.annotation.NonNull;

import java.util.List;

public class Records {
    private List<Course> records;

    public List<Course> getRecords() {
        return records;
    }

    public void setRecords(List<Course> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public String toString() {
        return "Records:{" +
                "records=" + records + '\'' +
                '}';
    }
}
