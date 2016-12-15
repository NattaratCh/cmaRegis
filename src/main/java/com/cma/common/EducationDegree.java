package com.cma.common;

/**
 * Created by NATTARAT on 2016-12-15.
 */
public class EducationDegree {
    public EducationDegree(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private String value;
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
