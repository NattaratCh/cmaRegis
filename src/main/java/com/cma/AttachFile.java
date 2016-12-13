package com.cma;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class AttachFile {
    public AttachFile(){
        super();
    }

    public AttachFile(AttachFile attachFile) {
        this.uploadPhoto = attachFile.uploadPhoto;
        this.uploadNamecard = attachFile.uploadNamecard;
        this.uploadIdcard = attachFile.uploadIdcard;
        this.uploadPassport = attachFile.uploadPassport;
        this.uploadApec = attachFile.uploadApec;
        this.uploadPf = attachFile.uploadPf;
        this.uploadSlip1 = attachFile.uploadSlip1;
        this.uploadSlip2 = attachFile.uploadSlip2;
        this.uploadHoldingTax = attachFile.uploadHoldingTax;
        this.photoFileName = attachFile.photoFileName;
        this.namecardFileName = attachFile.namecardFileName;
        this.idcardFileName = attachFile.idcardFileName;
        this.passportFileName = attachFile.passportFileName;
        this.apecFileName = attachFile.apecFileName;
        this.pfFileName = attachFile.pfFileName;
        this.slip1FileName = attachFile.slip1FileName;
        this.slip2FileName = attachFile.slip2FileName;
        this.holdingTaxFileName = attachFile.holdingTaxFileName;
    }


    @Transient
    private byte[] photo;
    @Transient
    private byte[] namecard;
    @Transient
    private byte[] idcard;
    @Transient
    private byte[] passport;
    @Transient
    private byte[] apec;
    @Transient
    @Size(max = 1000)
    private byte[] pf;
    @Transient
    @Size(max = 1000)
    private byte[] slip1;
    @Transient
    @Size(max = 1000)
    private byte[] slip2;
    @Transient
    @Size(max = 1000)
    private byte[] holdingTax;


    Boolean uploadPhoto;
    Boolean uploadNamecard;
    Boolean uploadIdcard;
    Boolean uploadPassport;
    Boolean uploadApec;
    Boolean uploadPf;
    Boolean uploadSlip1;
    Boolean uploadSlip2;
    Boolean uploadHoldingTax;

    String photoFileName;
    String namecardFileName;
    String idcardFileName;
    String passportFileName;
    String apecFileName;
    String pfFileName;
    String slip1FileName;
    String slip2FileName;
    String holdingTaxFileName;

    @ManyToOne
    private Student stdProfile;
}
