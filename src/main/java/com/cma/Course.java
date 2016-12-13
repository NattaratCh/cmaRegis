package com.cma;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Course {
    @Column(length = 20)
    private String code;

    @Column(length = 50)
    private String name;
}
