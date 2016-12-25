package com.cma.web;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 26/12/2555
 * Time: 16:05 น.
 * To change this template use File | Settings | File Templates.
 */
public class Std_profile_part4 {
    @NotNull
    private Long id;

    @NotNull
    private Long version;

    private String collboratorFirstName;
    private String collboratorLastName;

    @Pattern(regexp = "[0-9]|",message = "เบอร์โทรศัพท์ส่วนหน้าต้องเป็นตัวเลขเท่านั้น")
    private String frontcollboratorTel;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนกลางต้องเป็นตัวเลข 4 หลักเท่านั้น")
    private String middlecollboratorTel;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนท้ายต้องเป็นตัวเลข 4 หลักเท่านั้น")
    private String lastcollboratorTel;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9]|[0-9]|",message = "เบอร์โทรศัพท์ถึงต้องเป็นตัวเลขเท่านั้น")
    private String collboratorTel_2;

    @NotNull
    private String extcollboratorTel;


    @Pattern(regexp = "[0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนหน้าต้องเป็นตัวเลข 2 หลักเท่านั้น")
    private String frontcollboratorMobile;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนกลางต้องเป็นตัวเลข 4 หลักเท่านั้น")
    private String middlecollboratorMobile;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนท้ายต้องเป็นตัวเลข 4 หลักเท่านั้น")
    private String lastcollboratorMobile;


    @Pattern(regexp = "[0-9]|",message = "เบอร์โทรศัพท์ส่วนหน้าต้องเป็นตัวเลขเท่านั้น")
    private String frontcollboratorFax;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนกลางต้องเป็นตัวเลข 4 หลักเท่านั้น")
    private String middlecollboratorFax;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนท้ายต้องเป็นตัวเลข 4 หลักเท่านั้น")
    private String lastcollboratorFax;

    @Email(message = "รูปแบบEmailไม่ถูกต้อง")
    private String collboratorEmail;

    private String collboratorLineId;

    @Pattern(regexp = "[0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนหน้าต้องเป็นตัวเลข 2 หลักเท่านั้น")
    @Column(length=2)
    private String frontdriverTel;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนกลางต้องเป็นตัวเลข 4 หลักเท่านั้น")
    @Column(length=4)
    private String middledriverTel;

    @Pattern(regexp = "[0-9][0-9][0-9][0-9]|",message = "เบอร์โทรศัพท์ส่วนท้ายต้องเป็นตัวเลข 4 หลักเท่านั้น")
    @Column(length=4)
    private String lastdriverTel;

    private String collboratorTitle;
    private String collboratorPosition;

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

    public String getCollboratorFirstName() {
        return collboratorFirstName;
    }

    public void setCollboratorFirstName(String collboratorFirstName) {
        this.collboratorFirstName = collboratorFirstName;
    }

    public String getCollboratorLastName() {
        return collboratorLastName;
    }

    public void setCollboratorLastName(String collboratorLastName) {
        this.collboratorLastName = collboratorLastName;
    }

    public String getFrontcollboratorTel() {
        return frontcollboratorTel;
    }

    public void setFrontcollboratorTel(String frontcollboratorTel) {
        this.frontcollboratorTel = frontcollboratorTel;
    }

    public String getMiddlecollboratorTel() {
        return middlecollboratorTel;
    }

    public void setMiddlecollboratorTel(String middlecollboratorTel) {
        this.middlecollboratorTel = middlecollboratorTel;
    }

    public String getLastcollboratorTel() {
        return lastcollboratorTel;
    }

    public void setLastcollboratorTel(String lastcollboratorTel) {
        this.lastcollboratorTel = lastcollboratorTel;
    }

    public String getCollboratorTel_2() {
        return collboratorTel_2;
    }

    public void setCollboratorTel_2(String collboratorTel_2) {
        this.collboratorTel_2 = collboratorTel_2;
    }

    public String getExtcollboratorTel() {
        return extcollboratorTel;
    }

    public void setExtcollboratorTel(String extcollboratorTel) {
        this.extcollboratorTel = extcollboratorTel;
    }

    public String getFrontcollboratorMobile() {
        return frontcollboratorMobile;
    }

    public void setFrontcollboratorMobile(String frontcollboratorMobile) {
        this.frontcollboratorMobile = frontcollboratorMobile;
    }

    public String getMiddlecollboratorMobile() {
        return middlecollboratorMobile;
    }

    public void setMiddlecollboratorMobile(String middlecollboratorMobile) {
        this.middlecollboratorMobile = middlecollboratorMobile;
    }

    public String getLastcollboratorMobile() {
        return lastcollboratorMobile;
    }

    public void setLastcollboratorMobile(String lastcollboratorMobile) {
        this.lastcollboratorMobile = lastcollboratorMobile;
    }

    public String getFrontcollboratorFax() {
        return frontcollboratorFax;
    }

    public void setFrontcollboratorFax(String frontcollboratorFax) {
        this.frontcollboratorFax = frontcollboratorFax;
    }

    public String getMiddlecollboratorFax() {
        return middlecollboratorFax;
    }

    public void setMiddlecollboratorFax(String middlecollboratorFax) {
        this.middlecollboratorFax = middlecollboratorFax;
    }

    public String getLastcollboratorFax() {
        return lastcollboratorFax;
    }

    public void setLastcollboratorFax(String lastcollboratorFax) {
        this.lastcollboratorFax = lastcollboratorFax;
    }

    public String getCollboratorEmail() {
        return collboratorEmail;
    }

    public void setCollboratorEmail(String collboratorEmail) {
        this.collboratorEmail = collboratorEmail;
    }

    public String getCollboratorLineId() {
        return collboratorLineId;
    }

    public void setCollboratorLineId(String collboratorLineId) {
        this.collboratorLineId = collboratorLineId;
    }

    public String getFrontdriverTel() {
        return frontdriverTel;
    }

    public void setFrontdriverTel(String frontdriverTel) {
        this.frontdriverTel = frontdriverTel;
    }

    public String getMiddledriverTel() {
        return middledriverTel;
    }

    public void setMiddledriverTel(String middledriverTel) {
        this.middledriverTel = middledriverTel;
    }

    public String getLastdriverTel() {
        return lastdriverTel;
    }

    public void setLastdriverTel(String lastdriverTel) {
        this.lastdriverTel = lastdriverTel;
    }

    public String getCollboratorTitle() {
        return collboratorTitle;
    }

    public void setCollboratorTitle(String collboratorTitle) {
        this.collboratorTitle = collboratorTitle;
    }

    public String getCollboratorPosition() {
        return collboratorPosition;
    }

    public void setCollboratorPosition(String collboratorPosition) {
        this.collboratorPosition = collboratorPosition;
    }
}
