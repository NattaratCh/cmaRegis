package com.cma;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Batch implements Serializable {

    @NotNull
    @Column(unique = true)
    private String nameTh;

    @NotNull
    @Column(unique = true)
    private String nameEn;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date start_date;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date end_date;

    @OneToMany(orphanRemoval = true ,fetch = FetchType.EAGER, mappedBy = "studentClass")
    @OrderBy("id ASC")
    private Set<Student> std_profileSet = new HashSet<Student>();

    @Transient
    private CommonsMultipartFile file1;

    @Transient
    private CommonsMultipartFile file2;

    @Transient
    private CommonsMultipartFile file3;

    @Transient
    private CommonsMultipartFile file4;

    @Transient
    private CommonsMultipartFile file5;

    private String filename1;
    private String filename2;

    private String filename3;
    private String filename4;
    private String filename5;

    private Boolean uploadfile1;
    private Boolean uploadfile2;
    private Boolean uploadfile3;
    private Boolean uploadfile4;
    private Boolean uploadfile5;

    private String remark;
    private String type;
    private String activityCalendar;
    private String directory;

    @Transient
    private CommonsMultipartFile activityCalendarFile;

    @Transient
    private CommonsMultipartFile directoryFile;

    private Integer number;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
