package com.cma.web;

import com.cma.Children_profile;
import com.cma.Education_profile;
import com.cma.Training_profile;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by NATTARAT on 2016-12-23.
 */
public class ExtraProfileList {
    private List<Education_profile> education_profiles;

    private List<Training_profile> training_profiles;

    private String marriedStatus;

    private String spouseFirstName;

    private String spouseLastName;

    private String spouseCareer;

    private String spouseInstitution;

    private String spouseBdateString;

    @DateTimeFormat(style = "M-")
    private Date spouseBirthDay;

    private String spouseRace;

    private String spouseNationality;

    private String spouseReligion;

    private List<Children_profile> children_profiles;

    public List<Education_profile> getEducation_profiles() {
        return education_profiles;
    }

    public void setEducation_profiles(List<Education_profile> education_profiles) {
        this.education_profiles = education_profiles;
    }

    public List<Training_profile> getTraining_profiles() {
        return training_profiles;
    }

    public void setTraining_profiles(List<Training_profile> training_profiles) {
        this.training_profiles = training_profiles;
    }

    public String getMarriedStatus() {
        return marriedStatus;
    }

    public void setMarriedStatus(String marriedStatus) {
        this.marriedStatus = marriedStatus;
    }

    public String getSpouseFirstName() {
        return spouseFirstName;
    }

    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public String getSpouseCareer() {
        return spouseCareer;
    }

    public void setSpouseCareer(String spouseCareer) {
        this.spouseCareer = spouseCareer;
    }

    public String getSpouseInstitution() {
        return spouseInstitution;
    }

    public void setSpouseInstitution(String spouseInstitution) {
        this.spouseInstitution = spouseInstitution;
    }

    public String getSpouseBdateString() {
        return spouseBdateString;
    }

    public void setSpouseBdateString(String spouseBdateString) {
        this.spouseBdateString = spouseBdateString;
    }

    public Date getSpouseBirthDay() {
        return spouseBirthDay;
    }

    public void setSpouseBirthDay(Date spouseBirthDay) {
        this.spouseBirthDay = spouseBirthDay;
    }

    public String getSpouseRace() {
        return spouseRace;
    }

    public void setSpouseRace(String spouseRace) {
        this.spouseRace = spouseRace;
    }

    public String getSpouseNationality() {
        return spouseNationality;
    }

    public void setSpouseNationality(String spouseNationality) {
        this.spouseNationality = spouseNationality;
    }

    public String getSpouseReligion() {
        return spouseReligion;
    }

    public void setSpouseReligion(String spouseReligion) {
        this.spouseReligion = spouseReligion;
    }

    public List<Children_profile> getChildren_profiles() {
        return children_profiles;
    }

    public void setChildren_profiles(List<Children_profile> children_profiles) {
        this.children_profiles = children_profiles;
    }
}

