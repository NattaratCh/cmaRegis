package com.cma.utils;

import com.cma.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.cma.common.authenManager.genPassword;
import static com.cma.common.authenManager.sha256;

/**
 * Created by NATTARAT on 2016-12-22.
 */
public class StudentUtils {
    private static Log log = LogFactory.getLog(StudentUtils.class);

    private static DateFormat passwordFormat = new SimpleDateFormat("ddMMMyyyy", Locale.US);
    private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private static DateFormat dMyFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("th","TH"));

    public static String generateUsername(Student student, Batch batch){
        if(student == null || batch == null){
            return null;
        }else{
            String username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+student.getFirstnameEn().toLowerCase();
            String uniqueUsername = generateUniqueUsername(batch, username.toLowerCase(), student, 1);
            log.info("generateUsername() | uniqueUsername : "+uniqueUsername);
            return uniqueUsername.toLowerCase();
        }
    }

    public static String generateUniqueUsername(Batch batch, String username, Student student, int i){ // i =start with 1
        UserWeb userWeb = UserWeb.getUserWeb(username);
        if(userWeb != null){
            String lastname = student.getLastnameEn().substring(0,i);
            username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+student.getFirstnameEn().toLowerCase()+lastname;
            username = username.toLowerCase();
            log.info("generateUniqueUsername() | username : "+username+" | i :"+i);
            return generateUniqueUsername(batch, username, student, i+1);
        }else{
            return username;
        }
    }

    public static String bCryptEncode(String password){
        if(password == null){
            return null;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public static Student copyStudent(Student student){
        log.info("copy student");
        Student returnStudent = new Student();
        returnStudent.setTitleTh(student.getTitleTh());
        returnStudent.setFirstnameTh(student.getFirstnameTh());
        returnStudent.setLastnameTh(student.getLastnameTh());
        returnStudent.setFullNameTh(student.getFullNameTh());
        returnStudent.setTitleEn(student.getTitleEn());
        returnStudent.setFirstnameEn(student.getFirstnameEn());
        returnStudent.setLastnameEn(student.getLastnameEn());
        returnStudent.setFullNameEn(student.getFullNameEn());
        returnStudent.setNickname(student.getNickname());
        returnStudent.setSex(student.getSex());

        String _birthDate = student.getBdateString();
        if(_birthDate != null && !_birthDate.equals("")){
            try{
                Date birthDate = df.parse(_birthDate);
                String bDateStr = dMyFormat.format(birthDate);
                returnStudent.setBirthdate(birthDate);
                returnStudent.setBdateString(bDateStr);
            }catch (Exception e){

            }

        }


        returnStudent.setIdcardNo(student.getIdcardNo());
        returnStudent.setPassportNo(student.getPassportNo());
        returnStudent.setRopNo(student.getRopNo());

        returnStudent.setPositionTh(student.getPositionTh());
        returnStudent.setPositionEn(student.getPositionEn());
        returnStudent.setInstitutionTh(student.getInstitutionTh());
        returnStudent.setInstitutionEn(student.getInstitutionEn());
        returnStudent.setInstitutionNo(student.getInstitutionNo());
        returnStudent.setInstitutionMooNo(student.getInstitutionMooNo());
        returnStudent.setInstitutionBuilding(student.getInstitutionBuilding());
        returnStudent.setInstitutionFloor(student.getInstitutionFloor());
        returnStudent.setInstitutionSoi(student.getInstitutionSoi());
        returnStudent.setInstitutionStreet(student.getInstitutionStreet());
        returnStudent.setInstitutionSubdistrict(student.getInstitutionSubdistrict());
        returnStudent.setInstitutionDistrict(student.getInstitutionDistrict());
        returnStudent.setInstitutionProvince(student.getInstitutionProvince());
        returnStudent.setInstitutionPostalCode(student.getInstitutionPostalCode());
        returnStudent.setWorkFullAddress(student.getWorkFullAddress());
        returnStudent.setWorktel1(student.getWorktel1());
        returnStudent.setWorktel2(student.getWorktel2());
        returnStudent.setWorkfax(student.getWorkfax());

        returnStudent.setMobile1(student.getMobile1());
        returnStudent.setMobile2(student.getMobile2());
        returnStudent.setEmail(student.getEmail());
        returnStudent.setLineId(student.getLineId());
        returnStudent.setFacebook(student.getFacebook());

        returnStudent.setHomeNo(student.getHomeNo());
        returnStudent.setMooNo(student.getMooNo());
        returnStudent.setBuilding(student.getBuilding());
        returnStudent.setVillage(student.getVillage());
        returnStudent.setFloor(student.getFloor());
        returnStudent.setSoi(student.getSoi());
        returnStudent.setStreet(student.getStreet());
        returnStudent.setSubdistrict(student.getSubdistrict());
        returnStudent.setDistrict(student.getDistrict());
        returnStudent.setProvince(student.getProvince());
        returnStudent.setPostalCode(student.getPostalCode());
        returnStudent.setHomeFullAddress(student.getHomeFullAddress());
        returnStudent.setHometel1(student.getHometel1());
        returnStudent.setHometel2(student.getHometel2());
        returnStudent.setHomefax(student.getHomefax());

        returnStudent.setSendingAddress(student.getSendingAddress());
        returnStudent.setMarriedStatus(student.getMarriedStatus());
        returnStudent.setSpouseFirstName(student.getSpouseFirstName());
        returnStudent.setSpouseLastName(student.getSpouseLastName());
        returnStudent.setSpouseCareer(student.getSpouseCareer());
        returnStudent.setSpouseInstitution(student.getSpouseInstitution());

        _birthDate = student.getSpouseBdateString();
        if(_birthDate != null && !_birthDate.equals("")){
            try{
                Date birthDate = df.parse(_birthDate);
                String bDateStr = dMyFormat.format(birthDate);
                returnStudent.setSpouseBirthDay(birthDate);
                returnStudent.setSpouseBdateString(bDateStr);
            }catch (Exception e){

            }

        }

        returnStudent.setSpouseRace(student.getSpouseRace());
        returnStudent.setSpouseNationality(student.getSpouseNationality());
        returnStudent.setSpouseReligion(student.getSpouseReligion());
        returnStudent.setCollboratorFirstName(student.getCollboratorFirstName());
        returnStudent.setCollboratorLastName(student.getCollboratorLastName());
        returnStudent.setCollboratorFullName(student.getCollboratorFullName());
        returnStudent.setCollboratorTel(student.getCollboratorTel());
        returnStudent.setCollboratorMobile(student.getCollboratorMobile());
        returnStudent.setCollboratorFax(student.getCollboratorFax());
        returnStudent.setCollboratorEmail(student.getCollboratorEmail());
        returnStudent.setCollboratorLineId(student.getCollboratorLineId());

        returnStudent.setDriverTel(student.getDriverTel());
        returnStudent.setCarNo(student.getCarNo());
        returnStudent.setCarBrand(student.getCarBrand());
        returnStudent.setCarColor(student.getCarColor());
        returnStudent.setPlayGolf(student.getPlayGolf());
        returnStudent.setHandyCap(student.getHandyCap());

        returnStudent.setGeneralFood(student.isGeneralFood());
        returnStudent.setHalalFood(student.isHalalFood());
        returnStudent.setJFood(student.isJFood());
        returnStudent.setVegeterianFood(student.isVegeterianFood());
        returnStudent.setAllergiesSeaFood(student.isAllergiesSeaFood());
        returnStudent.setBeefFood(student.isBeefFood());

        returnStudent.setOtherFood(student.getOtherFood());
        returnStudent.setSmoking(student.getSmoking());
        returnStudent.setJacketSize(student.getJacketSize());
        returnStudent.setPoloSize(student.getPoloSize());
        returnStudent.setWaist(student.getWaist());
        returnStudent.setTall(student.getTall());
        returnStudent.setReceiptDetailName(student.getReceiptDetailName());
        returnStudent.setReceiptDetailAddress(student.getReceiptDetailAddress());
        returnStudent.setTaxId(student.getTaxId());
        returnStudent.setSubmitProfile(student.getSubmitProfile());
        returnStudent.setGroupName(student.getGroupName());
        returnStudent.setRetireFlag(student.isRetireFlag());
        returnStudent.persist();
        
        return returnStudent;
    }

    public static int getLastRecord(Long classId){
        EntityManager entityManager = UserRegis.entityManager();
        Query query = entityManager.createQuery("select max(x.id) from UserRegis x WHERE x.studentProfile.studentClass.id = :class_id");
        query.setParameter("class_id", classId);
        List resultList = query.getResultList();
        if(resultList.get(0)!=null){
            Long lastId = (Long)resultList.get(0);
            UserRegis lastUserRegis = UserRegis.findUserRegis(lastId);
            String lastUsername = lastUserRegis.getUsername();
            lastUsername = lastUsername.substring(lastUsername.length()-3);
            return Integer.parseInt(lastUsername)+1;
        }
        else return 1;
    }

    public static Student createStudent(Student student, String dataState, Batch batch){
        if(student != null){
            if(dataState == null || dataState.equals(StudentDataState.INIT)){
                try{
                    student.persist();

                    log.info("createStudent() | add userRegis for student id : "+student.getId());
                    int maxUserId=getLastRecord(batch.getId());
                    maxUserId ++;

                    UserRegis userRegis = new UserRegis();
                    userRegis.setEnabled(true);
                    String username = batch.getNameEn() + "_" + (String.format("%03d", maxUserId));
                    userRegis.setUsername(username.toLowerCase());
                    String password = genPassword(8);
                    userRegis.setPassword(sha256(password));
                    userRegis.setStudentProfile(student);

                    AttachFile attachFile = new AttachFile();
                    attachFile.setUploadSlip2(false);
                    attachFile.setUploadSlip1(false);
                    attachFile.setUploadPf(false);
                    attachFile.setUploadPassport(false);
                    attachFile.setUploadPhoto(false);
                    attachFile.setUploadApec(false);
                    attachFile.setUploadNamecard(false);
                    attachFile.setUploadIdcard(false);
                    attachFile.setStdProfile(student);
                    attachFile.persist();

                    UserRegisRole role_unchange = UserRegisRole.findUserRegisRoleByRoleName("ROLE_USER_UNCHANGE");
                    userRegis.setUserRole(role_unchange);
                    userRegis.persist();

                    MapStudent mapStudent = new MapStudent();
                    mapStudent.setInitStudent(student);
                    mapStudent.persist();
                    log.info("createStudent() | Successfully create INIT student id : "+student.getId());
                }catch (Exception e){
                    e.printStackTrace();
                }

            } else if(dataState.equals(StudentDataState.REVISED)){
                try{
                    student.persist();

                    log.info("create uptodate student");
                    Student uptodateStudent = copyStudent(student);
                    uptodateStudent.setDataState(StudentDataState.UPTODATE);
                    uptodateStudent.persist();

                    AttachFile attachFile = new AttachFile();
                    attachFile.setUploadSlip2(false);
                    attachFile.setUploadSlip1(false);
                    attachFile.setUploadPf(false);
                    attachFile.setUploadPassport(false);
                    attachFile.setUploadPhoto(false);
                    attachFile.setUploadApec(false);
                    attachFile.setUploadNamecard(false);
                    attachFile.setUploadIdcard(false);
                    attachFile.setStdProfile(student);
                    attachFile.persist();

                    log.info("create() | create userweb for student id : "+uptodateStudent.getId());
                    UserWeb userWeb = new UserWeb();
                    String username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+student.getFirstnameEn().toLowerCase();
                    String password = batch.getCourse().getCode()+batch.getNumber();
                    if(student.getBirthdate() != null){
                        password = passwordFormat.format(student.getBirthdate());
                    }

                    userWeb.setUsername(generateUsername(student, batch));
                    userWeb.setPassword(bCryptEncode(password));
                    userWeb.persist();

                    uptodateStudent.setUserWeb(userWeb);

                    WebRole webRole = WebRole.getWebRole("ROLE_USER");
                    if(webRole != null){
                        // map user_web with role
                        log.info("create() | create userWebRole");
                        UserWebRole userWebRole = new UserWebRole();
                        userWebRole.setUserWeb(userWeb);
                        userWebRole.setWebRole(webRole);
                        userWebRole.persist();
                    }

                    log.info("create() | create map student");
                    MapStudent mapStudent = new MapStudent();
                    mapStudent.setRevisedStudent(student);
                    mapStudent.setUpToDateStudent(uptodateStudent);
                    mapStudent.persist();
                    log.info("createStudent() | Successfully create REVISED student id : "+student.getId());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return student;
    }
}
