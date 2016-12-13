package com.cma;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.Date;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UserRegis {

    @NotNull
    @Column(unique = true)
    private String username;

    private String password;

    @NotNull
    private Boolean enabled;

    private String password1;

    private String password2;

    private String password3;

    private String password4;

    private String password5;

    private int pointer;

    private int loginTimes;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style="M-")
    private Date lastChangePwd;

    @ManyToOne
    private Student studentProfile;

    @OneToOne
    private UserRegisRole userRole;
}
