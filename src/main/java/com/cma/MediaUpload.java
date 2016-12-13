package com.cma;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Transient;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class MediaUpload {
    @Transient
    private byte[] content;
}
