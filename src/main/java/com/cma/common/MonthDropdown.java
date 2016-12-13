package com.cma.common;

import java.lang.String;

public class MonthDropdown{

    public MonthDropdown(String value,String label){
        this.monthValue = value;
        this.monthLabel = label;
    }

    public String getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(String monthValue) {
        this.monthValue = monthValue;
    }

    public String getMonthLabel() {
        return monthLabel;
    }

    public void setMonthLabel(String monthLabel) {
        this.monthLabel = monthLabel;
    }

    private String monthValue;
    private String monthLabel;
}
