package com.cma.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 26/12/2555
 * Time: 15:52 น.
 * To change this template use File | Settings | File Templates.
 */
public class Std_profile_part3 {
    @NotNull
    private Long id;

    @NotNull
    private Long version;
    //bachelor degree
    private String bdField;
    private String bdUniversity;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    private String bdYear;

    //master degree
    private String mdField;
    private String mdUniversity;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    private String mdYear;

    //ph.D
    private String phdField;
    private String phdUniversity;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    private String phdYear;

    //Training
    private String trainingName;
    private String trainingInstitute;
    private String trainingClass;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    private String traingYear;

    public String getBdField() {
        return bdField;
    }

    public void setBdField(String bdField) {
        this.bdField = bdField;
    }

    public String getBdUniversity() {
        return bdUniversity;
    }

    public void setBdUniversity(String bdUniversity) {
        this.bdUniversity = bdUniversity;
    }

    public String getBdYear() {
        return bdYear;
    }

    public void setBdYear(String bdYear) {
        this.bdYear = bdYear;
    }

    public String getMdField() {
        return mdField;
    }

    public void setMdField(String mdField) {
        this.mdField = mdField;
    }

    public String getMdUniversity() {
        return mdUniversity;
    }

    public void setMdUniversity(String mdUniversity) {
        this.mdUniversity = mdUniversity;
    }

    public String getMdYear() {
        return mdYear;
    }

    public void setMdYear(String mdYear) {
        this.mdYear = mdYear;
    }

    public String getPhdField() {
        return phdField;
    }

    public void setPhdField(String phdField) {
        this.phdField = phdField;
    }

    public String getPhdUniversity() {
        return phdUniversity;
    }

    public void setPhdUniversity(String phdUniversity) {
        this.phdUniversity = phdUniversity;
    }

    public String getPhdYear() {
        return phdYear;
    }

    public void setPhdYear(String phdYear) {
        this.phdYear = phdYear;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingInstitute() {
        return trainingInstitute;
    }

    public void setTrainingInstitute(String trainingInstitute) {
        this.trainingInstitute = trainingInstitute;
    }

    public String getTrainingClass() {
        return trainingClass;
    }

    public void setTrainingClass(String trainingClass) {
        this.trainingClass = trainingClass;
    }

    public String getTraingYear() {
        return traingYear;
    }

    public void setTraingYear(String traingYear) {
        this.traingYear = traingYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
