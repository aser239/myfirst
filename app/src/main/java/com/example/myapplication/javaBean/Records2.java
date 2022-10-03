package com.example.myapplication.javaBean;

import java.util.List;

public class Records2 {
    private List<Records2Detail> records2;

    public List<Records2Detail> getRecords2() {
        return records2;
    }

    public void setRecords2(List<Records2Detail> records2) {
        this.records2 = records2;
    }

    @Override
    public String toString() {
        return "Records2{" +
                "records2=" + records2 +
                '}';
    }
}
