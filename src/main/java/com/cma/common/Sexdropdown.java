package com.cma.common;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 9/5/2556
 * Time: 16:42 น.
 * To change this template use File | Settings | File Templates.
 */
public class Sexdropdown {

    public Sexdropdown(String sexValue, String sexLabel) {
        this.sexValue = sexValue;
        this.sexLabel = sexLabel;
    }

    public String getSexValue() {
        return sexValue;
    }

    public void setSexValue(String sexValue) {
        this.sexValue = sexValue;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }

    private String sexValue;
    private String sexLabel;


}
