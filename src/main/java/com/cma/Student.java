package com.cma;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Student {

    @OneToMany(orphanRemoval = true,fetch = FetchType.EAGER,mappedBy = "studentProfile")
    private Set<UserRegis> userRegisSet = new HashSet<UserRegis>();

    @ManyToOne
    private Batch studentClass;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "stdProfile")
    private AttachFile attachFile;

    @OneToMany(orphanRemoval = true ,fetch = FetchType.EAGER, mappedBy = "studentProfile")
    @OrderBy(value = "id")
    private Set<Children_profile> children_profileSet = new HashSet<Children_profile>();

    @OneToMany(orphanRemoval = true ,fetch = FetchType.EAGER, mappedBy = "studentProfile")
    @OrderBy(value = "degree")
    private Set<Education_profile> education_profileSet = new HashSet<Education_profile>();


    @OneToMany(orphanRemoval = true ,fetch = FetchType.EAGER, mappedBy = "studentProfile")
    @OrderBy(value = "id")
    private Set<Training_profile> training_profileSet = new HashSet<Training_profile>();


    private String titleTh;

     
    private String titleEn;

     
/*    @Size(min=1, max=5, message="Please Enter less than 5 Charercters")*/
    private String firstnameTh;

     
    private String firstnameEn;

     
    private String lastnameTh;

     
    private String lastnameEn;

    private String fullNameTh;

    private String fullNameEn;
     
    private String nickname;

     
    private String sex;

    @Column(length = 30)
    private String bdateString;

    @DateTimeFormat(style = "M-")
    private Date birthdate;

    @Column(length=20)
    private String idcardNo;

    @Column(length=20)
    private String passportNo;

    @Column(length=20)
    private String ropNo;

    @Column(length=50)
    private String positionTh;

    @Column(length=50)
    private String positionEn;

    @Column(length=50)
    private String institutionTh;

    @Column(length=50)
    private String institutionEn;

    private Boolean retireFlag;

    @Column(length=50)
    private String enrollType;

    @Column(length=255)
    private String workFullAddress;

    @Column(length=50)
    private String institutionNo;

    @Column(length=100)
    private String institutionMooNo;

    @Column(length=100)
    private String institutionBuilding;

    @Column(length=100)
    private String institutionFloor;

    @Column(length=100)
    private String institutionSoi;

    @Column(length=100)
    private String institutionStreet;

    @Column(length=100)
    private String institutionSubdistrict;

    @Column(length=100)
    private String institutionDistrict;

    @Column(length=100)
    private String institutionProvince;

    @Column(length=5)
    private String institutionPostalCode;

    @Column(length=1)
    private String frontworktel1;
    @Column(length=4)
    private String middleworktel1;
    @Column(length=4)
    private String lastworktel1;
    @Column(length=4)
    private String worktel1_2;
    @Column(length=30)
    private String extworktel1;

    @Column(length=50)
    private String worktel1;

    @Column(length=1)
    private String frontworktel2;
    @Column(length=4)
    private String middleworktel2;
    @Column(length=4)
    private String lastworktel2;
    @Column(length=4)
    private String worktel2_2;
    @Column(length=30)
    private String extworktel2;

    @Column(length=50)
    private String worktel2;


    @Column(length=1)
    private String frontworkfax;
    @Column(length=4)
    private String middleworkfax;
    @Column(length=4)
    private String lastworkfax;
    @Column(length=50)
    private String workfax;

    @Column(length=2)
    private String frontmobile1;
    @Column(length=4)
    private String middlemobile1;
    @Column(length=4)
    private String lastmobile1;
    @Column(length=50)
    private String mobile1;


    @Column(length=2)
    private String frontmobile2;
    @Column(length=4)
    private String middlemobile2;
    @Column(length=4)
    private String lastmobile2;
    @Column(length=50)
    private String mobile2;

    @Column(length=50)
    @Email
    private String email;

    @Column(length=50)
    @Email
    private String workEmail;

    @Column(length=50)
    private String lineId;

    @Column(length=100)
    private String facebook;

    @Column(length=50)
    @Email
    private String email2;

    @Column(length=50)
    private String homeNo;

    @Column(length=50)
    private String mooNo;

    @Column(length=50)
    private String building;

    @Column(length=50)
    private String village;

    @Column(length=50)
    private String floor;

    @Column(length=50)
    private String soi;

    @Column(length=50)
    private String street;

    @Column(length=50)
    private String subdistrict;

    @Column(length=50)
    private String district;

    @Column(length=50)
    private String province;

    @Column(length=5)
    private String postalCode;

    @Column(length=255)
    private String homeFullAddress;

    @Column(length=1)
    private String fronthometel1;
    @Column(length=4)
    private String middlehometel1;
    @Column(length=4)
    private String lasthometel1;
    @Column(length=4)
    private String hometel1_2;
    @Column(length=30)
    private String exthometel1;

    @Column(length=50)
    private String hometel1;

    @Column(length=1)
    private String fronthometel2;
    @Column(length=4)
    private String middlehometel2;
    @Column(length=4)
    private String lasthometel2;
    @Column(length=4)
    private String hometel2_2;
    @Column(length=30)
    private String exthometel2;

    @Column(length=50)
    private String hometel2;

    @Column(length=1)
    private String fronthomefax;
    @Column(length=4)
    private String middlehomefax;
    @Column(length=4)
    private String lasthomefax;

    @Column(length=50)
    private String homefax;

     
    private String sendingAddress;

    ///////////////////////////////Family Info.
    @Column(length=50)
    private String marriedStatus;

    @Column(length=50)
    private String spouseFirstName;

    @Column(length=50)
    private String spouseLastName;

    @Column(length=50)
    private String spouseCareer;

    @Column(length=50)
    private String spouseInstitution;

    @Column(length = 30)
    private String spouseBdateString;

    @DateTimeFormat(style = "M-")
    private Date spouseBirthDay;

    @Column(length=50)
    private String spouseRace;

    @Column(length=50)
    private String spouseNationality;

    @Column(length=50)
    private String spouseReligion;

    @Column(length=50)
    private String childrenFirstName;

    @Column(length=50)
    private String childrenLastName;

    @Column(length=50)
    private String childrenCareer;

    @Column(length=30)
    private String childrenBdateString;

    @DateTimeFormat(style = "M-")
    private Date childrenBirthday;

    @Column(length=50)
    private String childrenRace;

    @Column(length=50)
    private String childrenNationality;

    @Column(length=50)
    private String childrenReligion;

    ///////////////////////////////// Education and Training Info.
    //bachelor degree
    private String bdField;
    private String bdUniversity;
    private String bdYear;
    //master degree
    private String mdField;
    private String mdUniversity;
    private String mdYear;
    //ph.D
    private String phdField;
    private String phdUniversity;
    private String phdYear;
    //Training
    private String trainingName;
    private String trainingInstitute;
    private String trainingClass;
    private String traingYear;

    //////////////////////////////// Collaborator Info.

    private String collboratorTitle;
    private String collboratorFirstName;
    private String collboratorLastName;
    private String collboratorFullName;
    private String collboratorPosition;

    @Column(length=1)
    private String frontcollboratorTel;
    @Column(length=4)
    private String middlecollboratorTel;
    @Column(length=4)
    private String lastcollboratorTel;
    @Column(length=4)
    private String collboratorTel_2;
    @Column(length=30)
    private String extcollboratorTel;

    private String collboratorTel;

    @Column(length=2)
    private String frontcollboratorMobile;
    @Column(length=4)
    private String middlecollboratorMobile;
    @Column(length=4)
    private String lastcollboratorMobile;

    private String collboratorMobile;

    @Column(length=1)
    private String frontcollboratorFax;
    @Column(length=4)
    private String middlecollboratorFax;
    @Column(length=4)
    private String lastcollboratorFax;
    private String collboratorFax;
    private String collboratorEmail;
    @Column(length=50)
    private String collboratorLineId;
    @Column(length=2)
    private String frontdriverTel;
    @Column(length=4)
    private String middledriverTel;
    @Column(length=4)
    private String lastdriverTel;
    @Column(length=12)
    private String driverTel;

    ///////////////////////////////// More Info
    private String carNo;
    private String carBrand;
    private String carColor;
    private String playGolf;
    private String handyCap;
    private boolean generalFood;
    private boolean halalFood;
    private boolean jFood;
    private boolean vegeterianFood;
    private boolean allergiesSeaFood;
    private boolean beefFood;
    private String otherFood;
    private String smoking;
    private String jacketSize;
    private String poloSize;
    private String waist;
    private String tall;
    private String receiptDetailName;

    @Column(length=50)
    private String taxId;
    private String receiptDetailAddress;

    private Boolean submitProfile;

    private Integer permission; //0-5

    @Column(length=10)
    private String dataState; //INIT,REVISED,UP-TO-DATE

    private String groupName;

    @OneToOne
    @JoinColumn(name = "user_web_id")
    private UserWeb userWeb;

    public static List<Student> listStudent(String dataState, Batch batch){
        if(batch == null){
            return null;
        }
        EntityManager entityManager = Student.entityManager();
        Query query = entityManager.createQuery(" select s from Student s where s.dataState= :dataState and s.studentClass= :batch order by s.id ");
        query.setParameter("dataState", dataState);
        query.setParameter("batch", batch);
        List resultList = query.getResultList();
        return resultList;
    }
}
