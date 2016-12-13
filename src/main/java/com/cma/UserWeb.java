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
    public UserWeb() {
        super();
        this.lastLogin = null;
        this.lastUpdate = new Date();
        this.enabled = Boolean.TRUE;
        this.accountExpired = Boolean.FALSE;
        this.accountLocked = Boolean.FALSE;
        this.loginCount = 0;
        this.oldPasswdList = "";
        this.passwordExpired = Boolean.FALSE;
    }

    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean enabled;
    private Date lastLogin;
    private Date lastUpdate;
    private Integer loginCount;

    @Column(length=255)
    private String oldPasswdList;

    @Column(length=255)
    private String password;

    private Boolean passwordExpired;

    @Column(length=255)
    private String username;
}
