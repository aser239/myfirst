package com.example.myapplication.javaBean;


import java.util.List;

public class Records2 {
    private List<Records2Detail> records;

    public List<Records2Detail> getRecords() {
        return records;
    }

    public void setRecords(List<Records2Detail> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "records{" +
                "records=" + records +
                '}';
    }
}
