package com.cma;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;


@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Education_profile {
    public Education_profile(){
        super();
    }

    public Education_profile(Education_profile education_profile){
        this.degree = education_profile.degree;
        this.field = education_profile.field;
        this.university = education_profile.university;
        this.graduateYear = education_profile.graduateYear;
    }

    String degree;

    String field;

    String university;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    String graduateYear;

    @ManyToOne
    private Student studentProfile;
}
