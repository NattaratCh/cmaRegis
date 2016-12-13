package com.cma;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import java.util.Date;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UserWeb {
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean enabled;
    private Date lastLogin;
    private Date lastUpdate;
    private Integer loginCount;

    @Column(length=255)
    private String oldPasswordList;

    @Column(length=255)
    private String password;

    private Boolean passwordExpired;

    @Column(length=255)
    private String username;
}
