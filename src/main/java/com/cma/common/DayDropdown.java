package com.cma.common;
import java.lang.String;

public class DayDropdown{
    public DayDropdown(String value,String label){
        this.dayValue = value;
        this.dayLabel = label;
    }

    public String getDayValue() {
        return dayValue;
    }

    public void setDayValue(String dayValue) {
        this.dayValue = dayValue;
    }

    public String getDayLabel() {
        return dayLabel;
    }

    public void setDayLabel(String dayLabel) {
        this.dayLabel = dayLabel;
    }

    private String dayValue;
    private String dayLabel;
}
