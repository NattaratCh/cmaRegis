package com.cma.web;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 26/12/2555
 * Time: 15:24 น.
 * To change this template use File | Settings | File Templates.
 */
public class Std_profile_part2 {

    @NotNull
    private Long id;

    @NotNull
    private Long version;

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

    private String childrenFirstName;

    private String childrenLastName;

    private String childrenCareer;

    private String childrenBdateString;

    @DateTimeFormat(style = "M-")
    private Date childrenBirthday;

    private String childrenRace;

    private String childrenNationality;

    private String childrenReligion;


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

    public String getChildrenBdateString() {
        return childrenBdateString;
    }

    public void setChildrenBdateString(String childrenBdateString) {
        this.childrenBdateString = childrenBdateString;
    }

    public String getChildrenFirstName() {
        return childrenFirstName;
    }

    public void setChildrenFirstName(String childrenFirstName) {
        this.childrenFirstName = childrenFirstName;
    }

    public String getChildrenLastName() {
        return childrenLastName;
    }

    public void setChildrenLastName(String childrenLastName) {
        this.childrenLastName = childrenLastName;
    }

    public String getChildrenCareer() {
        return childrenCareer;
    }

    public void setChildrenCareer(String childrenCareer) {
        this.childrenCareer = childrenCareer;
    }

    public Date getChildrenBirthday() {
        return childrenBirthday;
    }

    public void setChildrenBirthday(Date childrenBirthday) {
        this.childrenBirthday = childrenBirthday;
    }

    public String getChildrenRace() {
        return childrenRace;
    }

    public void setChildrenRace(String childrenRace) {
        this.childrenRace = childrenRace;
    }

    public String getChildrenNationality() {
        return childrenNationality;
    }

    public void setChildrenNationality(String childrenNationality) {
        this.childrenNationality = childrenNationality;
    }

    public String getChildrenReligion() {
        return childrenReligion;
    }

    public void setChildrenReligion(String childrenReligion) {
        this.childrenReligion = childrenReligion;
    }


    public String getSpouseInstitution() {
        return spouseInstitution;
    }

    public void setSpouseInstitution(String spouseInstitution) {
        this.spouseInstitution = spouseInstitution;
    }
}
