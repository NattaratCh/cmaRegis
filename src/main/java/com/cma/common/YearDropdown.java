package com.cma.common;

import java.lang.String;

public class YearDropdown{
    public YearDropdown(String value,String label){
        this.yearValue = value;
        this.yearLabel = label;
    }

    public String getYearValue() {
        return yearValue;
    }

    public void setYearValue(String yearValue) {
        this.yearValue = yearValue;
    }

    public String getYearLabel() {
        return yearLabel;
    }

    public void setYearLabel(String yearLabel) {
        this.yearLabel = yearLabel;
    }

    private String yearValue;
    private String yearLabel;
}
