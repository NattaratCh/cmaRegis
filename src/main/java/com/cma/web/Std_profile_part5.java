package com.cma.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 26/12/2555
 * Time: 17:49 น.
 * To change this template use File | Settings | File Templates.
 */
public class Std_profile_part5 {
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getPlayGolf() {
        return playGolf;
    }

    public void setPlayGolf(String playGolf) {
        this.playGolf = playGolf;
    }

    public String getHandyCap() {
        return handyCap;
    }

    public void setHandyCap(String handyCap) {
        this.handyCap = handyCap;
    }

    public boolean isGeneralFood() {
        return generalFood;
    }

    public void setGeneralFood(boolean generalFood) {
        this.generalFood = generalFood;
    }

    public boolean isHalalFood() {
        return halalFood;
    }

    public void setHalalFood(boolean halalFood) {
        this.halalFood = halalFood;
    }

    public boolean isjFood() {
        return jFood;
    }

    public void setjFood(boolean jFood) {
        this.jFood = jFood;
    }

    public boolean isVegeterianFood() {
        return vegeterianFood;
    }

    public void setVegeterianFood(boolean vegeterianFood) {
        this.vegeterianFood = vegeterianFood;
    }

    public boolean isAllergiesSeaFood() {
        return allergiesSeaFood;
    }

    public void setAllergiesSeaFood(boolean allergiesSeaFood) {
        this.allergiesSeaFood = allergiesSeaFood;
    }

    public boolean isBeefFood() {
        return beefFood;
    }

    public void setBeefFood(boolean beefFood) {
        this.beefFood = beefFood;
    }

    public String getOtherFood() {
        return otherFood;
    }

    public void setOtherFood(String otherFood) {
        this.otherFood = otherFood;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getJacketSize() {
        return jacketSize;
    }

    public void setJacketSize(String jacketSize) {
        this.jacketSize = jacketSize;
    }

    public String getPoloSize() {
        return poloSize;
    }

    public void setPoloSize(String poloSize) {
        this.poloSize = poloSize;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getTall() {
        return tall;
    }

    public void setTall(String tall) {
        this.tall = tall;
    }

    public String getReceiptDetailName() {
        return receiptDetailName;
    }

    public void setReceiptDetailName(String receiptDetailName) {
        this.receiptDetailName = receiptDetailName;
    }

    public String getReceiptDetailAddress() {
        return receiptDetailAddress;
    }

    public void setReceiptDetailAddress(String receiptDetailAddress) {
        this.receiptDetailAddress = receiptDetailAddress;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    @NotNull
    private Long id;

    @NotNull
    private Long version;

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

    private String carNo;
    private String carBrand;
    private String carColor;
    private String playGolf;
    @Pattern(regexp = "[0-9][0-9]|",message = "handyCapเป็นเลขไม่เกิน2หลัก")
    private String handyCap;
    private boolean generalFood;
    private boolean halalFood;
    private boolean jFood;
    private boolean vegeterianFood;
    private boolean allergiesSeaFood;
    private boolean beefFood;
    private String otherFood;
    private String smoking;
    private String jacketSize;
    private String poloSize;

    @Pattern(regexp = "[1-9][0-9]|",message = "Invalid waist")
    private String waist;
    @Pattern(regexp = "[1-9][0-9][0-9]|",message = "Invalid tall")
    private String tall;

    @NotNull
    private String receiptDetailName;
    @NotNull
    private String receiptDetailAddress;

    private String taxId;

    private Integer permission;

}
