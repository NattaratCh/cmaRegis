package com.cma;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Training_profile {
    public Training_profile(){
        super();
    }

    public Training_profile(Training_profile training_profile) {
        this.trainingName = training_profile.trainingName;
        this.trainingInstitute = training_profile.trainingInstitute;
        this.trainingClass = training_profile.trainingClass;
        this.trainingYear = training_profile.trainingYear;
    }

    String trainingName;

    String trainingInstitute;

    String trainingClass;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    String trainingYear;

    @ManyToOne
    private Student studentProfile;
}
