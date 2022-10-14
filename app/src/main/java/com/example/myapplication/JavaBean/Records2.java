package com.example.myapplication.JavaBean;


import androidx.annotation.NonNull;

import java.util.List;

public class Records2 {
    private List<Records2Detail> records;

    public List<Records2Detail> getRecords() {
        return records;
    }

    public void setRecords(List<Records2Detail> records) {
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
