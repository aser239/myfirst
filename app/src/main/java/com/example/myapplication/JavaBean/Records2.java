package com.example.myapplication.JavaBean;


import androidx.annotation.NonNull;

import java.util.List;

public class Records2 {
    private List<StudentSignListDetail> records;

    public List<StudentSignListDetail> getRecords() {
        return records;
    }

    public void setRecords(List<StudentSignListDetail> records) {
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
