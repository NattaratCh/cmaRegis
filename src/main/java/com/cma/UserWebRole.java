package com.cma;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "")
public class UserWebRole implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserWeb userWeb;

    @OneToOne
    @JoinColumn(name = "role_id")
    private WebRole webRole;
}
