package com.cma.web;

import com.cma.*;
import com.cma.common.EducationDegree;
import com.cma.common.MailManager;
import com.cma.common.Sexdropdown;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.cma.common.authenManager.genPassword;
import static com.cma.common.authenManager.sha256;

@RequestMapping("/std_profiles")
@Controller
@RooWebScaffold(path = "std_profiles", formBackingObject = Student.class)
public class Std_profileController {

    protected EntityManager entityManager;
    private SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy",new Locale("th","TH"));

    private static Log log = LogFactory.getLog(Std_profileController.class);

    @RequestMapping(value = "/attachfileListForm")
    public String attachfileListForm(@RequestParam(value = "cmaClass", required = false) Long cmaClass,Model uiModel, HttpServletRequest httpServletRequest){
        List cmaClassList = Batch.findAllBatches();
        String dataState = httpServletRequest.getParameter("dataState");

        if(dataState == null || dataState.equals("") || dataState.equalsIgnoreCase(StudentDataState.INIT)){
            dataState = StudentDataState.INIT;
        }else if(dataState.equalsIgnoreCase(StudentDataState.REVISED)){
            dataState = StudentDataState.REVISED;
        }else if(dataState.equalsIgnoreCase(StudentDataState.UPTODATE)){
            dataState = StudentDataState.UPTODATE;
        }


        if(cmaClass==null){
            if(cmaClassList.size()==0){
                uiModel.addAttribute("classList",cmaClassList);
                return "std_profiles/attachList";
            }
            else{
                Batch first = Batch.findAllBatches().get(0);
                cmaClass = first.getId();
            }
        }
        entityManager = Student.entityManager();
        Query query = entityManager.createQuery("select o from Student o where student_class= :class_id and data_state= :dataState");
        query.setParameter("class_id",cmaClass);
        query.setParameter("dataState",dataState);

        List std_profiles = query.getResultList();
        uiModel.addAttribute("class_id",cmaClass);
        uiModel.addAttribute("classList",cmaClassList);
        uiModel.addAttribute("std_profiles",std_profiles);
        uiModel.addAttribute("dataState",dataState);
        return "std_profiles/attachList";
    }

    @RequestMapping( value = "/{id}",params = "deleteChildren")
    public String deleteChildren(@PathVariable("id") Long id, Model uiModel){
        //System.out.println(">>>>>>>>deleteChildren");
        Children_profile.entityManager().clear();
        Children_profile children_profile = Children_profile.findChildren_profile(id);
        Student parent = children_profile.getStudentProfile();
        parent.getChildren_profileSet().remove(children_profile);

        Long profile_id = parent.getId();
        children_profile.remove();
        children_profile.flush();
        return "redirect:/std_profiles/"+profile_id+"?form2";
    }

    @RequestMapping( value = "/{id}",params = "deleteTraining")
    public String deleteTraining(@PathVariable("id") Long id, Model uiModel){
        //System.out.println(">>>>>>>>deleteTraining");
        Training_profile.entityManager().clear();
        Training_profile training_profile = Training_profile.findTraining_profile(id);
        Student parent = training_profile.getStudentProfile();
        parent.getChildren_profileSet().remove(training_profile);

        Long profile_id = parent.getId();
        training_profile.remove();
        training_profile.flush();
        return "redirect:/std_profiles/"+profile_id+"?form3";
    }

    @RequestMapping( value = "/{id}",params = "deleteEducation")
    public String deleteEducation(@PathVariable("id") Long id, Model uiModel){
        //System.out.println(">>>>>>>>deleteEducation "+id+" ----- "+(education_profile==null));
        Education_profile.entityManager().clear();
        Education_profile education_profile = Education_profile.findEducation_profile(id);
        Student parent = education_profile.getStudentProfile();
        parent.getChildren_profileSet().remove(education_profile);

        Long profile_id = parent.getId();
        education_profile.remove();
        education_profile.flush();
        return "redirect:/std_profiles/"+profile_id+"?form3";
    }

    @RequestMapping(value = "/{id}", params = "index" ,produces = "text/html")
    public String index(@PathVariable("id") Long id, Model uiModel){
        Student std_profile = Student.findStudent(id);
        populateEditForm(uiModel, std_profile);
        uiModel.addAttribute("std_class",std_profile.getStudentClass());
        uiModel.addAttribute("message","บันทึกเรียบร้อย");
        return "std_profiles/success";
    }

    @RequestMapping(value = "/update5", method = RequestMethod.PUT , produces = "text/html")
    public String update5(@Valid Std_profile_part5 std_profile_part5, BindingResult bindingResult,Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("std_profile", std_profile_part5);
            uiModel.addAttribute("modelAttribute","std_profile_part5");
            addSelectedItem5(uiModel);
            return "std_profiles/update5";
        }

        Long id = Long.parseLong(httpServletRequest.getParameter("id"));
        Student std_profile = Student.findStudent(id);
        std_profile.setCarNo(std_profile_part5.getCarNo());
        std_profile.setCarBrand(std_profile_part5.getCarBrand());
        std_profile.setCarColor(std_profile_part5.getCarColor());
        std_profile.setPlayGolf(std_profile_part5.getPlayGolf());
        std_profile.setHandyCap(std_profile_part5.getHandyCap());
        std_profile.setGeneralFood(std_profile_part5.isGeneralFood());
        std_profile.setHalalFood(std_profile_part5.isHalalFood());
        std_profile.setJFood(std_profile_part5.isjFood());
        std_profile.setVegeterianFood(std_profile_part5.isVegeterianFood());
        std_profile.setAllergiesSeaFood(std_profile_part5.isAllergiesSeaFood());
        std_profile.setBeefFood(std_profile_part5.isBeefFood());
        std_profile.setOtherFood(std_profile_part5.getOtherFood());
        std_profile.setSmoking(std_profile_part5.getSmoking());
        std_profile.setJacketSize(std_profile_part5.getJacketSize());
        std_profile.setPoloSize(std_profile_part5.getPoloSize());
        std_profile.setWaist(std_profile_part5.getWaist());
        std_profile.setTall(std_profile_part5.getTall());
        std_profile.setReceiptDetailName(std_profile_part5.getReceiptDetailName());
        std_profile.setTaxId(std_profile_part5.getTaxId());
        std_profile.setReceiptDetailAddress(std_profile_part5.getReceiptDetailAddress());

        String submitButton = httpServletRequest.getParameter("submitButton");
       /* System.out.println(">>>>>>>>>Update5 is submited. "+id);*/
        if(submitButton.equals("ยืนยัน")){
            std_profile.setSubmitProfile(true);
            std_profile.merge();
            return "redirect:/std_profiles/"+id+"?index";
        }
        else {
            std_profile.merge();
            return "redirect:/std_profiles/"+id+"?form4";
        }
    }

    @RequestMapping(value = "/update4", method = RequestMethod.PUT , produces = "text/html")
    public String update4(@Valid Std_profile_part4 std_profile_part4, BindingResult bindingResult,Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("std_profile", std_profile_part4);
            uiModel.addAttribute("modelAttribute","std_profile_part4");
            return "std_profiles/update4";
        }
        Long id = Long.parseLong(httpServletRequest.getParameter("id"));
        Student std_profile = Student.findStudent(id);
        std_profile.setCollboratorFirstName(std_profile_part4.getCollboratorFirstName());
        std_profile.setCollboratorLastName(std_profile_part4.getCollboratorLastName());

        std_profile.setFrontcollboratorTel(std_profile_part4.getFrontcollboratorTel());
        std_profile.setMiddlecollboratorTel(std_profile_part4.getMiddlecollboratorTel());
        std_profile.setLastcollboratorTel(std_profile_part4.getLastcollboratorTel());
        std_profile.setCollboratorTel_2(std_profile_part4.getCollboratorTel_2());
        std_profile.setExtcollboratorTel(std_profile_part4.getExtcollboratorTel());
        std_profile.setCollboratorTel(std_profile_part4.getFrontcollboratorTel()+std_profile_part4.getMiddlecollboratorTel()+std_profile_part4.getLastcollboratorTel());

        std_profile.setFrontcollboratorMobile(std_profile_part4.getFrontcollboratorMobile());
        std_profile.setMiddlecollboratorMobile(std_profile_part4.getMiddlecollboratorMobile());
        std_profile.setLastcollboratorMobile(std_profile_part4.getLastcollboratorMobile());
        std_profile.setCollboratorMobile(std_profile_part4.getFrontcollboratorMobile()+std_profile_part4.getMiddlecollboratorMobile()+std_profile_part4.getLastcollboratorMobile());

        std_profile.setFrontcollboratorFax(std_profile_part4.getFrontcollboratorFax());
        std_profile.setMiddlecollboratorFax(std_profile_part4.getMiddlecollboratorFax());
        std_profile.setLastcollboratorFax(std_profile_part4.getLastcollboratorFax());
        std_profile.setCollboratorFax(std_profile_part4.getFrontcollboratorFax()+std_profile_part4.getMiddlecollboratorFax()+std_profile_part4.getLastcollboratorFax());

        std_profile.setCollboratorEmail(std_profile_part4.getCollboratorEmail());
        std_profile.setCollboratorLineId(std_profile_part4.getCollboratorLineId());
        std_profile.setFrontdriverTel(std_profile_part4.getFrontdriverTel());
        std_profile.setMiddledriverTel(std_profile_part4.getMiddledriverTel());
        std_profile.setLastdriverTel(std_profile_part4.getLastdriverTel());
        std_profile.setDriverTel(std_profile_part4.getFrontdriverTel()+std_profile_part4.getMiddledriverTel()+std_profile_part4.getLastdriverTel());
        std_profile.merge();

        String submitButton = httpServletRequest.getParameter("submitButton");
        /*System.out.println(">>>>>>>>>Update4 is submited. "+id);*/
        if(submitButton.equals("ถัดไป"))
            return "redirect:/std_profiles/"+id+"?form5";
        else
            return "redirect:/std_profiles/"+id+"?form3";
    }
    @RequestMapping(value = "/update3", method = RequestMethod.PUT , produces = "text/html")
    public String update3(@Valid Std_profile_part3 std_profile_part3, BindingResult bindingResult,Model uiModel, HttpServletRequest httpServletRequest) {
        Long id = Long.parseLong(httpServletRequest.getParameter("id"));
        Student std_profile = Student.findStudent(id);
        String submitButton = httpServletRequest.getParameter("submitButton");
        /*System.out.println(">>>>>>>>>Update3 is submited. "+id);*/

        if(submitButton.equals("ถัดไป"))
            return "redirect:/std_profiles/"+id+"?form4";
        else if(submitButton.equals("กลับ"))
            return "redirect:/std_profiles/"+id+"?form2";
        else {
            /// Binding Error Incident
            if (bindingResult.hasErrors()) {
                uiModel.addAttribute("std_profile", std_profile_part3);
                uiModel.addAttribute("modelAttribute","std_profile_part3");
                List bdList = findEducationProfileList("Bachelor",id);
                List mdList = findEducationProfileList("Master",id);
                List phdList = findEducationProfileList("PhD",id);
                List trainingList = findTrainingProfileList(id);
                uiModel.addAttribute("education_profile_bd",bdList);
                uiModel.addAttribute("education_profile_md",mdList);
                uiModel.addAttribute("education_profile_phd",phdList);
                uiModel.addAttribute("training_profile",trainingList);
                return "std_profiles/update3";
            }

            /// Each Degree
            if(submitButton.equals("บันทึกข้อมูล การศึกษาปริญญาตรี")){
                Education_profile education_profile = new Education_profile();
                education_profile.setDegree("Bachelor");
                education_profile.setField(std_profile_part3.getBdField());
                education_profile.setUniversity(std_profile_part3.getBdUniversity());
                if(!std_profile_part3.getBdYear().equals(""))
                    education_profile.setGraduateYear(std_profile_part3.getBdYear());
                education_profile.setStudentProfile(std_profile);
                education_profile.persist();
                return "redirect:/std_profiles/"+id+"?form3";
            }
            else if(submitButton.equals("บันทึกข้อมูล การศึกษาปริญญาโท")){
                Education_profile education_profile = new Education_profile();
                education_profile.setDegree("Master");
                education_profile.setField(std_profile_part3.getMdField());
                education_profile.setUniversity(std_profile_part3.getMdUniversity());
                if(!std_profile_part3.getMdYear().equals(""))
                    education_profile.setGraduateYear(std_profile_part3.getMdYear());
                education_profile.setStudentProfile(std_profile);
                education_profile.persist();
                return "redirect:/std_profiles/"+id+"?form3";
            }
            else if(submitButton.equals("บันทึกข้อมูล การศึกษาปริญญาเอก")){
                Education_profile education_profile = new Education_profile();
                education_profile.setDegree("PhD");
                education_profile.setField(std_profile_part3.getPhdField());
                education_profile.setUniversity(std_profile_part3.getPhdUniversity());
                if(!std_profile_part3.getPhdYear().equals(""))
                    education_profile.setGraduateYear(std_profile_part3.getPhdYear());
                education_profile.setStudentProfile(std_profile);
                education_profile.persist();
                return "redirect:/std_profiles/"+id+"?form3";
            }
            else if(submitButton.equals("บันทึกข้อมูล หลักสูตรฝึกอบรม")){
                Training_profile training_profile = new Training_profile();
                training_profile.setTrainingName(std_profile_part3.getTrainingName());
                training_profile.setTrainingInstitute(std_profile_part3.getTrainingInstitute());
                training_profile.setTrainingClass(std_profile_part3.getTrainingClass());
                if(!std_profile_part3.getTraingYear().equals(""))
                    training_profile.setTrainingYear(std_profile_part3.getTraingYear());
                training_profile.setStudentProfile(std_profile);
                training_profile.persist();
                return "redirect:/std_profiles/"+id+"?form3";
            }
            else return "";
        }
    }
    @RequestMapping(value = "/update2", method = RequestMethod.PUT , produces = "text/html")
    public String update2(@Valid Std_profile_part2 std_profile_part2, BindingResult bindingResult,Model uiModel, HttpServletRequest httpServletRequest) {
        Long id = Long.parseLong(httpServletRequest.getParameter("id"));
        String submitButton = httpServletRequest.getParameter("submitButton");
        /*System.out.println(">>>>>>>>>Update2 is submited. "+id+" :submitButton "+httpServletRequest.getParameter("submitButton"));*/

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("std_profile", std_profile_part2);
            uiModel.addAttribute("modelAttribute","std_profile_part2");
            List childrenList = findChildrenProfileList(id);
            uiModel.addAttribute("children_profile",childrenList);
            addSelectedItem2(uiModel);
            addDateTimeFormatPatterns(uiModel);
            return "std_profiles/update2";
        }

        Student std_profile = Student.findStudent(id);
        std_profile.setMarriedStatus(std_profile_part2.getMarriedStatus());
        std_profile.setSpouseFirstName(std_profile_part2.getSpouseFirstName());
        std_profile.setSpouseLastName(std_profile_part2.getSpouseLastName());
        std_profile.setSpouseCareer(std_profile_part2.getSpouseCareer());
        std_profile.setSpouseInstitution(std_profile_part2.getSpouseInstitution());
        std_profile.setSpouseBdateString(std_profile_part2.getSpouseBdateString());
        Date bday=null;
        try {
            bday = df.parse(std_profile_part2.getSpouseBdateString());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        std_profile.setSpouseBirthDay(bday);
        std_profile.setSpouseRace(std_profile_part2.getSpouseRace());
        std_profile.setSpouseNationality(std_profile_part2.getSpouseNationality());
        std_profile.setSpouseReligion(std_profile_part2.getSpouseReligion());
        std_profile.merge();

        if(submitButton.equals("ถัดไป"))
            return "redirect:/std_profiles/"+id+"?form3";
        else if (submitButton.equals("กลับ"))
            return "redirect:/std_profiles/"+id+"?form1";
        else if (submitButton.equals("บันทึกข้อมูล ประวัติบุตร/ธิดา")){
            Children_profile children_profile = new Children_profile();
            children_profile.setFirstName(std_profile_part2.getChildrenFirstName());
            children_profile.setLastName(std_profile_part2.getChildrenLastName());
            children_profile.setCareer(std_profile_part2.getChildrenCareer());
            children_profile.setChildrenBdateString(std_profile_part2.getChildrenBdateString());
            Date childrenbday=null;
            try {
                childrenbday = df.parse(std_profile_part2.getChildrenBdateString());
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            children_profile.setBirthday(childrenbday);
            children_profile.setRace(std_profile_part2.getChildrenRace());
            children_profile.setNationality(std_profile_part2.getChildrenNationality());
            children_profile.setReligion(std_profile_part2.getChildrenReligion());
            children_profile.setStudentProfile(std_profile);
            //std_profile.getChildrenProfileList().add(children_profile);
            //std_profile.merge();
            children_profile.persist();
            return "redirect:/std_profiles/"+id+"?form2";
        }
        else return "";
    }
    @RequestMapping(value = "/update1", method = RequestMethod.PUT , produces = "text/html")
    public String update1(@Valid Std_profile_part1 std_profile_part1, BindingResult bindingResult,Model uiModel, HttpServletRequest httpServletRequest) {
        Long id = Long.parseLong(httpServletRequest.getParameter("id"));
        Student std_profile = Student.findStudent(id);
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("std_profile", std_profile_part1);
            std_profile_part1.setFirstnameTh(std_profile.getFirstnameTh());
            std_profile_part1.setLastnameTh(std_profile.getLastnameTh());
            uiModel.addAttribute("modelAttribute","std_profile_part1");
            addSelectedItem1(uiModel);
            return "std_profiles/update1";
        }

        std_profile.setTitleTh(std_profile_part1.getTitleTh());
        std_profile.setTitleEn(std_profile_part1.getTitleEn().toUpperCase());
        if(std_profile_part1.getFirstnameTh()!=null)
            std_profile.setFirstnameTh(std_profile_part1.getFirstnameTh());
        std_profile.setFirstnameEn(std_profile_part1.getFirstnameEn().toUpperCase());
        if(std_profile_part1.getLastnameTh()!=null)
            std_profile.setLastnameTh(std_profile_part1.getLastnameTh());
        std_profile.setLastnameEn(std_profile_part1.getLastnameEn().toUpperCase());
        std_profile.setNickname(std_profile_part1.getNickname());
        std_profile.setSex(std_profile_part1.getSex());
        std_profile.setBdateString(std_profile_part1.getBdateString());
        Date bday=null;
        try {
            bday = df.parse(std_profile_part1.getBdateString());
            System.out.println("birthdate : "+bday);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        std_profile.setBirthdate(bday);
        std_profile.setIdcardNo(std_profile_part1.getIdcardNo());
        std_profile.setPassportNo(std_profile_part1.getPassportNo());
        std_profile.setRopNo(std_profile_part1.getRopNo());
        std_profile.setPositionTh(std_profile_part1.getPositionTh());
        std_profile.setPositionEn(std_profile_part1.getPositionEn());
        std_profile.setInstitutionTh(std_profile_part1.getInstitutionTh());
        std_profile.setInstitutionEn(std_profile_part1.getInstitutionEn());
        /*std_profile.setEnrollType(std_profile_part1.getEnrollType());*/
        std_profile.setInstitutionNo(std_profile_part1.getInstitutionNo());
        std_profile.setInstitutionMooNo(std_profile_part1.getInstitutionMooNo());
        std_profile.setInstitutionBuilding(std_profile_part1.getInstitutionBuilding());
        std_profile.setInstitutionFloor(std_profile_part1.getInstitutionFloor());
        std_profile.setInstitutionSoi(std_profile_part1.getInstitutionSoi());
        std_profile.setInstitutionStreet(std_profile_part1.getInstitutionStreet());
        std_profile.setInstitutionSubdistrict(std_profile_part1.getInstitutionSubdistrict());
        std_profile.setInstitutionDistrict(std_profile_part1.getInstitutionDistrict());
        std_profile.setInstitutionProvince(std_profile_part1.getInstitutionProvince());
        std_profile.setInstitutionPostalCode(std_profile_part1.getInstitutionPostalCode());


        //---------------------------------------------Worktel1-----------------------------------------------------------
        std_profile.setFrontworktel1(std_profile_part1.getFrontworktel1());
        std_profile.setMiddleworktel1(std_profile_part1.getMiddleworktel1());
        std_profile.setLastworktel1(std_profile_part1.getLastworktel1());
        std_profile.setWorktel1_2(std_profile_part1.getWorktel1_2());
        std_profile.setExtworktel1(std_profile_part1.getExtworktel1());
        std_profile.setWorktel1(std_profile_part1.getFrontworktel1() + std_profile_part1.getMiddleworktel1() + std_profile_part1.getLastworktel1());


        //---------------------------------------------Worktel2-----------------------------------------------------------
        std_profile.setFrontworktel2(std_profile_part1.getFrontworktel2());
        std_profile.setMiddleworktel2(std_profile_part1.getMiddleworktel2());
        std_profile.setLastworktel2(std_profile_part1.getLastworktel2());
        std_profile.setWorktel2_2(std_profile_part1.getWorktel2_2());
        std_profile.setExtworktel2(std_profile_part1.getExtworktel2());
        std_profile.setWorktel2(std_profile_part1.getFrontworktel2() + std_profile_part1.getMiddleworktel2() + std_profile_part1.getLastworktel2());

        //---------------------------------------------Workfax-----------------------------------------------------------
        std_profile.setFrontworkfax(std_profile_part1.getFrontworkfax());
        std_profile.setMiddleworkfax(std_profile_part1.getMiddleworkfax());
        std_profile.setLastworkfax(std_profile_part1.getLastworkfax());
        std_profile.setWorkfax(std_profile_part1.getFrontworkfax() + std_profile_part1.getMiddleworkfax() + std_profile_part1.getLastworkfax());

        //---------------------------------------------Mobile1-----------------------------------------------------------
        std_profile.setFrontmobile1(std_profile_part1.getFrontmobile1());
        std_profile.setMiddlemobile1(std_profile_part1.getMiddlemobile1());
        std_profile.setLastmobile1(std_profile_part1.getLastmobile1());
        std_profile.setMobile1(std_profile_part1.getFrontmobile1()+std_profile_part1.getMiddlemobile1()+std_profile_part1.getLastmobile1());

        //---------------------------------------------Mobile2-----------------------------------------------------------
        std_profile.setFrontmobile2(std_profile_part1.getFrontmobile2());
        std_profile.setMiddlemobile2(std_profile_part1.getMiddlemobile2());
        std_profile.setLastmobile2(std_profile_part1.getLastmobile2());
        std_profile.setMobile2(std_profile_part1.getFrontmobile2() + std_profile_part1.getMiddlemobile2() + std_profile_part1.getLastmobile2());

        std_profile.setEmail(std_profile_part1.getEmail());
        std_profile.setLineId(std_profile_part1.getLineId());
        std_profile.setHomeNo(std_profile_part1.getHomeNo());
        std_profile.setMooNo(std_profile_part1.getMooNo());
        std_profile.setBuilding(std_profile_part1.getBuilding());
        std_profile.setVillage(std_profile_part1.getVillage());
        std_profile.setFloor(std_profile_part1.getFloor());
        std_profile.setSoi(std_profile_part1.getSoi());
        std_profile.setStreet(std_profile_part1.getStreet());
        std_profile.setSubdistrict(std_profile_part1.getSubdistrict());
        std_profile.setDistrict(std_profile_part1.getDistrict());
        std_profile.setProvince(std_profile_part1.getProvince());
        std_profile.setPostalCode(std_profile_part1.getPostalCode());

        //---------------------------------------------Hometel1-----------------------------------------------------------
        std_profile.setFronthometel1(std_profile_part1.getFronthometel1());
        std_profile.setMiddlehometel1(std_profile_part1.getMiddlehometel1());
        std_profile.setLasthometel1(std_profile_part1.getLasthometel1());
        std_profile.setHometel1_2(std_profile_part1.getHometel1_2());
        std_profile.setExthometel1(std_profile_part1.getExthometel1());
        std_profile.setHometel1(std_profile_part1.getFronthometel1()+std_profile_part1.getMiddlehometel1()+std_profile_part1.getLasthometel1());

        //---------------------------------------------Hometel2-----------------------------------------------------------
        std_profile.setFronthometel2(std_profile_part1.getFronthometel2());
        std_profile.setMiddlehometel2(std_profile_part1.getMiddlehometel2());
        std_profile.setLasthometel2(std_profile_part1.getLasthometel2());
        std_profile.setHometel2_2(std_profile_part1.getHometel2_2());
        std_profile.setExthometel2(std_profile_part1.getExthometel2());
        std_profile.setHometel2(std_profile_part1.getFronthometel2() + std_profile_part1.getMiddlehometel2() + std_profile_part1.getLasthometel2());

        //---------------------------------------------Homefax-----------------------------------------------------------
        std_profile.setFronthomefax(std_profile_part1.getFronthomefax());
        std_profile.setMiddlehomefax(std_profile_part1.getMiddlehomefax());
        std_profile.setLasthomefax(std_profile_part1.getLasthomefax());
        std_profile.setHomefax(std_profile_part1.getFronthomefax()+std_profile_part1.getMiddlehomefax()+std_profile_part1.getLasthomefax());
        std_profile.setSendingAddress(std_profile_part1.getSendingAddress());
        std_profile.merge();

        /*System.out.println(">>>>>>>>>Update1 is submited. "+id);*/
        return "redirect:/std_profiles/"+id+"?form2";
    }

    @RequestMapping(value = "/{id}", params = "form5", produces = "text/html")
    public String updateForm5(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Student.findStudent(id));
        uiModel.addAttribute("modelAttribute", "student");
        addSelectedItem5(uiModel);
        return "std_profiles/update5";
    }

    @RequestMapping(value = "/{id}", params = "form4", produces = "text/html")
    public String updateForm4(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Student.findStudent(id));
        uiModel.addAttribute("modelAttribute", "student");
        return "std_profiles/update4";
    }

    @RequestMapping(value = "/{id}", params = "form3", produces = "text/html")
    public String updateForm3(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Student.findStudent(id));
        uiModel.addAttribute("modelAttribute", "student");
        List bdList = findEducationProfileList("Bachelor",id);
        List mdList = findEducationProfileList("Master",id);
        List phdList = findEducationProfileList("PhD",id);
        List trainingList = findTrainingProfileList(id);
        uiModel.addAttribute("education_profile_bd",bdList);
        uiModel.addAttribute("education_profile_md",mdList);
        uiModel.addAttribute("education_profile_phd",phdList);
        uiModel.addAttribute("training_profile",trainingList);
        return "std_profiles/update3";
    }

    @RequestMapping(value = "/{id}", params = "form2", produces = "text/html")
    public String updateForm2(@PathVariable("id") Long id, Model uiModel) {
         Student.findStudent(id);
        populateEditForm(uiModel, Student.findStudent(id));
        uiModel.addAttribute("modelAttribute", "student");
        List childrenList = findChildrenProfileList(id);
        uiModel.addAttribute("children_profile",childrenList);
        addSelectedItem2(uiModel);
        addDateTimeFormatPatterns(uiModel);
        return "std_profiles/update2";
    }

    @RequestMapping(value = "/{id}", params = "form1", produces = "text/html")
    public String updateForm1(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Student.findStudent(id));
        uiModel.addAttribute("modelAttribute", "student");
        addSelectedItem1(uiModel);
        return "std_profiles/update1";
    }

    @RequestMapping(value = "/{id}", params = "delete", produces = "text/html")
    public String delete2(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Student std_profile = Student.findStudent(id);
        Long class_id = std_profile.getStudentClass().getId();

        std_profile.remove();
        uiModel.asMap().clear();
        return "redirect:/std_profiles?cmaClass="+class_id;
    }

    @RequestMapping(value = "send" ,method = RequestMethod.POST, produces = "text/html")
    public String send( Model uiModel,HttpServletRequest request) {
        /*System.out.println(">>>>>>>>>>>>>>>>>>>send Mail Controller");*/
        String urlLink = request.getRequestURL().substring(0,request.getRequestURL().indexOf("std_profile"));
        String cmaClass = request.getParameter("cmaClass_id");
        /*System.out.println(">>"+request.getParameter("cmaClass_id"));
        System.out.println(">>"+request.getRequestURL().substring(0,request.getRequestURL().indexOf("std_profile")));*/
        String select_id[] = request.getParameterValues("checkbox");
        if(select_id==null){
            uiModel.addAttribute("warning_message","noselect");
            uiModel.addAttribute("std_profiles", Student.findAllStudents());
            return "redirect:/std_profiles?cmaClass="+cmaClass;
        }

        String password = null;
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
        MailManager mm = (MailManager) context.getBean("mailManager");

        for (int i=0;i<select_id.length;i++){

            Student student = Student.findStudent(Long.parseLong(select_id[i]));
            //System.out.println(">>>>>>>>>>>>>>>>>>>student Id : " + student.getId() + " Email : " + student.getEmail());
            Long userlogin_id = findUserLoginID(student.getId());
            UserRegis userRegis = UserRegis.findUserRegis(userlogin_id);

            String username = userRegis.getUsername();
            password = genPassword(8);
            userRegis.setPassword(sha256(password));
            userRegis.setPassword1(sha256(password));
            userRegis.setPassword2(null);
            userRegis.setPassword3(null);
            userRegis.setPassword4(null);
            userRegis.setPassword5(null);
            userRegis.setPointer(1);
            userRegis.merge();
            //System.out.println(">>>>>>>>>>>>>>>>>>>username&pass " + userlogin.getUsername() + ":" + password);
            sendEmailToStudent(mm,student,username,password,student.getEmail(),student.getEmail2(),urlLink);
        }
        uiModel.addAttribute("message","complete");
        uiModel.addAttribute("std_profiles", Student.findAllStudents());
        return "redirect:/std_profiles?cmaClass="+cmaClass;
    }

    private Long findUserLoginID(Long profile_id){
        /*System.out.println(">>>>>>>>>>>findUserLoginID Method");*/
        List allUserlogins = UserRegis.findAllUserRegises();
        int n = allUserlogins.size();
        for(int i=0;i<n;i++)
        {
            UserRegis userRegis = (UserRegis)allUserlogins.get(i);
            if(userRegis.getStudentProfile()!=null){
                if(profile_id.equals(userRegis.getStudentProfile().getId())){
                    return userRegis.getId();
                }
            }
        }
        return -1L;
    }

    private void sendEmailToStudent(MailManager mm,Student std_profile,String username,String password,String email,String email2,String urlLink){

        String receiver = std_profile.getTitleTh()+std_profile.getFirstnameTh()+" "+std_profile.getLastnameTh();
        String position = std_profile.getPositionTh();
        String institude = std_profile.getInstitutionTh();
        String remark = std_profile.getStudentClass().getRemark();
        urlLink = "https://regis.cma.in.th/";

        SimpleDateFormat formatdate = new SimpleDateFormat("d MMMMMM yyyy");
        formatdate.getDateInstance(DateFormat.SHORT, LocaleContextHolder.getLocale());
        String today = formatdate.format(new Date());
        String start_day = formatdate.format(std_profile.getStudentClass().getStart_date());
        String end_day = formatdate.format(std_profile.getStudentClass().getEnd_date());
        String std_class = std_profile.getStudentClass().getNameTh();

        StringBuffer mailMsg= new StringBuffer();
        System.out.println("test object "+std_profile.getStudentClass());
        if(std_profile.getStudentClass().getType().equals("Junior")){
            mailMsg.append("<div style=\"width:80%;\">");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr align=\"center\" style=\"width:100%;\"><td><img name=\"logo\" src=\""+urlLink+"cmaRegis/resources/images/logo-mail.jpg\" width=\"500\" height=\"80\" border=\"0\"></td></tr>");
            mailMsg.append("<tr><td style=\"padding-left:35em;\">");
            mailMsg.append("<span align=\"left\">"+today+"</span>");
            mailMsg.append("</td></tr></table>");
            mailMsg.append("<br/><br />");
            mailMsg.append("<table style=\"width: 100%;\">\n");
            mailMsg.append("<tr><td style=\"width:15px\">เรื่อง</td>");
            mailMsg.append("<td style=\"width:95%\">แจ้งขั้นตอนการสมัครเป็นนักศึกษา &quot;หลักสูตรผู้บริหารยุคใหม่ สถาบันวิทยาการตลาดทุน&quot; (Y-CMA) รุ่นที่ "+std_class+"<br />");
            mailMsg.append("</td></tr>");
            mailMsg.append("<tr><td style=\"width:15px;\">เรียน</td><td style=\"width: 95%;\">"+receiver+"</td></tr>");
            mailMsg.append("<tr><td>&nbsp;</td><td>"+position+"</td></tr>");
            mailMsg.append("<tr><td>&nbsp;</td><td>"+institude+"</td></tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/><br/>");
            mailMsg.append("<div style=\"width:100%;\">");
            mailMsg.append("<table style=\"width: 80%;\">");
            mailMsg.append("<tr>");
            mailMsg.append("<td style=\"width: 25%;\">สิ่งที่ส่งมาด้วย</td>");
            mailMsg.append("<td style=\"width: 75%;\">1. คำแนะนำขั้นตอนการสมัคร</td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td></td>");
            mailMsg.append("<td>2. ปฏิทินการศึกษา</td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td></td>");
            mailMsg.append("<td>3. ใบตอบรับการเข้าศึกษา</td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td></td>");
            mailMsg.append("<td>4. ใบสมัครสมาชิกสวตท.</td>");
            mailMsg.append("</tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ตามที่ คณะกรรมการอำนวยการ สถาบันวิทยาการตลาดทุน ได้พิจารณาเรียนเชิญท่านเข้าร่วมหลักสูตรผู้บริหารยุคใหม่ ");
            mailMsg.append("สถาบันวิทยาการตลาดทุน รุ่นที่ "+std_class+" (Y-CMA "+std_class+") ที่จะจัดขึ้นระหว่าง"+remark+" (รายละเอียดตามแนบ) นั้น ");
            mailMsg.append("ท่านจะได้รับหนังสือเรียนเชิญสมัครเป็นนักศึกษา “หลักสูตรผู้บริหารยุคใหม่ สถาบันวิทยาการตลาดทุน” รุ่นที่ "+std_class+" ลงนามโดยท่าน");
            mailMsg.append("กรรมการและผู้จัดการ ตลาดหลักทรัพย์แห่งประเทศไทย ทางไปรษณีย์ตามที่อยู่ที่ท่านแจ้งไว้");
            mailMsg.append("</td></tr></table>");
            mailMsg.append("<br>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td style=\"padding-left:3em;\">");
            mailMsg.append("ในโอกาสนี้ สถาบันฯ ใคร่ขอแจ้งขั้นตอนการสมัครเข้าเป็นนักศึกษา ดังนี้<br/>");
            mailMsg.append("1.\tตอบรับยืนยันการเข้าศึกษาในใบตอบรับการเข้าศึกษา และใบสมัครสมาชิกสมาคมนักศึกษาสถาบันวิทยาการตลาดทุน ส่งกลับมายังสถาบันฯ (รายละเอียดตามเอกสารแนบ) <br/>");
            mailMsg.append("2.\tกรอกข้อมูลการสมัครและส่งเอกสารประกอบการสมัครผ่าน<a href="+urlLink+" target=\"_blank\"> https://regis.cma.in.th </a> ซึ่งท่านมีชื่อผู้เข้าใช้งานและรหัสผ่าน ดังนี้");
            mailMsg.append("</td></tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr>");
            mailMsg.append("<td style=\"padding-left:6em; width:35%;\"><b>ชื่อผู้เข้าใช้งาน (user name): </b></td><td style=\"width:65%;\"><b>"+username+"</b></td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td style=\"padding-left:6em;\"><b>รหัสผ่าน (password): </b></td><td><b>"+password+"</b></td>");
            mailMsg.append("</tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;โดยท่านสามารถกรอกข้อมูลการสมัครผ่านเว็บไซด์ของสถาบันฯ ได้ตั้งแต่วันที่ "+start_day+" ถึงวันที่ "+end_day+" หากท่านมีข้อ");
            mailMsg.append("สงสัยในการเข้ากรอกข้อมูลการสมัคร กรุณาติดต่อคุณปิยาภา วัฒโน โทร. 0-2596-9508 โทรสาร 0-2596-9501 Email:");
            mailMsg.append("piyapa@set.or.th");
            mailMsg.append("</td></tr></table>");
            mailMsg.append("<br/><br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td style=\"padding-left:35em;\"><span align=\"left\">ขอแสดงความนับถือ</span></td></tr>");
            mailMsg.append("<tr><td style=\"padding-left:35em;\"><span align=\"left\">สถาบันวิทยาการตลาดทุน</span></td></tr>");
            mailMsg.append("</table>");
            mailMsg.append("</div>");
            mailMsg.append("</div>");
        }
        else {
            mailMsg.append("<div style=\"width:80%;\">");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr align=\"center\" style=\"width:100%;\"><td><img name=\"logo\" src=\""+urlLink+"cmaRegis/resources/images/logo-mail.jpg\" width=\"500\" height=\"80\" border=\"0\"></td></tr>");
            mailMsg.append("<tr><td style=\"padding-left:35em;\">");
            mailMsg.append("<span align=\"left\">"+today+"</span>");
            mailMsg.append("</td></tr></table>");
            mailMsg.append("<br/><br />");
            mailMsg.append("<table style=\"width: 100%;\">\n");
            mailMsg.append("<tr><td style=\"width:15px\">เรื่อง</td>");
            mailMsg.append("<td style=\"width:95%\">แจ้งขั้นตอนการสมัครเป็นนักศึกษา &quot;หลักสูตรผู้บริหารระดับสูง สถาบันวิทยาการตลาดทุน&quot; (หลักสูตร วตท.) รุ่นที่ "+std_class+"<br />");
            mailMsg.append("</td></tr>");
            mailMsg.append("<tr><td style=\"width:15px;\">เรียน</td><td style=\"width: 95%;\">"+receiver+"</td></tr>");
            mailMsg.append("<tr><td>&nbsp;</td><td>"+position+"</td></tr>");
            mailMsg.append("<tr><td>&nbsp;</td><td>"+institude+"</td></tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/><br/>");
            mailMsg.append("<div style=\"width:100%;\">");
            mailMsg.append("<table style=\"width: 80%;\">");
            mailMsg.append("<tr>");
            mailMsg.append("<td style=\"width: 25%;\">สิ่งที่ส่งมาด้วย</td>");
            mailMsg.append("<td style=\"width: 75%;\">1. คำแนะนำขั้นตอนการสมัคร</td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td></td>");
            mailMsg.append("<td>2. ปฏิทินการศึกษา</td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td></td>");
            mailMsg.append("<td>3. ใบตอบรับการเข้าศึกษา</td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td></td>");
            mailMsg.append("<td>4. ใบสมัครสมาชิกสวตท.</td>");
            mailMsg.append("</tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ตามที่ คณะกรรมการอำนวยการ สถาบันวิทยาการตลาดทุน ได้พิจารณาเรียนเชิญท่านเข้าร่วมหลักสูตรผู้บริหารระดับสูง ");
            mailMsg.append("สถาบันวิทยาการตลาดทุน รุ่นที่ "+std_class+" (วตท."+std_class+") ที่จะจัดขึ้นระหว่าง"+remark+" (รายละเอียดตามแนบ) นั้น ");
            mailMsg.append("ท่านจะได้รับหนังสือเรียนเชิญสมัครเป็นนักศึกษา “หลักสูตรผู้บริหารระดับสูง สถาบันวิทยาการตลาดทุน” รุ่นที่ "+std_class+" ลงนามโดยท่าน");
            mailMsg.append("กรรมการและผู้จัดการ ตลาดหลักทรัพย์แห่งประเทศไทย ทางไปรษณีย์ตามที่อยู่ที่ท่านแจ้งไว้");
            mailMsg.append("</td></tr></table>");
            mailMsg.append("<br>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td style=\"padding-left:3em;\">");
            mailMsg.append("ในโอกาสนี้ สถาบันฯ ใคร่ขอแจ้งขั้นตอนการสมัครเข้าเป็นนักศึกษา ดังนี้<br/>");
            mailMsg.append("1.\tตอบรับยืนยันการเข้าศึกษาในใบตอบรับการเข้าศึกษา และใบสมัครสมาชิกสมาคมนักศึกษาสถาบันวิทยาการตลาดทุน ส่งกลับมายังสถาบันฯ (รายละเอียดตามเอกสารแนบ) <br/>");
            mailMsg.append("2.\tกรอกข้อมูลการสมัครและส่งเอกสารประกอบการสมัครผ่าน<a href="+urlLink+" target=\"_blank\"> https://regis.cma.in.th </a> ซึ่งท่านมีชื่อผู้เข้าใช้งานและรหัสผ่าน ดังนี้");
            mailMsg.append("</td></tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr>");
            mailMsg.append("<td style=\"padding-left:6em; width:35%;\"><b>ชื่อผู้เข้าใช้งาน (user name): </b></td><td style=\"width:65%;\"><b>"+username+"</b></td>");
            mailMsg.append("</tr>");
            mailMsg.append("<tr>");
            mailMsg.append("<td style=\"padding-left:6em;\"><b>รหัสผ่าน (password): </b></td><td><b>"+password+"</b></td>");
            mailMsg.append("</tr>");
            mailMsg.append("</table>");
            mailMsg.append("<br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;โดยท่านสามารถกรอกข้อมูลการสมัครผ่านเว็บไซด์ของสถาบันฯ ได้ตั้งแต่วันที่ "+start_day+" ถึงวันที่ "+end_day+" หากท่านมีข้อ");
            mailMsg.append("สงสัยในการเข้ากรอกข้อมูลการสมัคร กรุณาติดต่อคุณปิยาภา วัฒโน โทร. 0-2596-9508 โทรสาร 0-2596-9501 Email:");
            mailMsg.append("piyapa@set.or.th");
            mailMsg.append("</td></tr></table>");
            mailMsg.append("<br/><br/>");
            mailMsg.append("<table style=\"width: 100%;\">");
            mailMsg.append("<tr><td style=\"padding-left:35em;\"><span align=\"left\">ขอแสดงความนับถือ</span></td></tr>");
            mailMsg.append("<tr><td style=\"padding-left:35em;\"><span align=\"left\">สถาบันวิทยาการตลาดทุน</span></td></tr>");
            mailMsg.append("</table>");
            mailMsg.append("</div>");
            mailMsg.append("</div>");
        }

        String senderEmail = "cma@set.or.th";
        String bccEmail = "piyapa@set.or.th";
        String mailSubject;
        if(std_profile.getStudentClass().getType().equals("Junior")){
            mailSubject = "แจ้งขั้นตอนการสมัครเป็นนักศีกษา \"หลักสูตรผู้บริหารยุคใหม่ สถาบันวิทยาการตลาดทุน\" (Y-CMA) รุ่นที่ "+std_class;
        }else {
            mailSubject = "แจ้งขั้นตอนการสมัครเป็นนักศีกษา \"หลักสูตรผู้บริหารระดับสูง สถาบันวิทยาการตลาดทุน\" (หลักสูตร วตท.) รุ่นที่ "+std_class;
        }
        mm.sendMail(std_profile,
                senderEmail,
                email,
                email2,
                bccEmail,
                mailSubject,
                mailMsg.toString());
    }

    private void addSelectedItem1(Model uiModel){
        //////////////////////////////////////////Sex
        List sexList = new ArrayList();
        /*sexList.add("");*/
        sexList.add(new Sexdropdown(" ","--โปรดเลือก--"));
        sexList.add(new Sexdropdown("ชาย","ชาย"));
        sexList.add(new Sexdropdown("หญิง","หญิง"));
        uiModel.addAttribute("sexList",sexList);

        //////////////////////////////////////////Year

        int maxyear = new Date().getYear()+1900-20;
        int minyear = maxyear-80;
        uiModel.addAttribute("maxyear",maxyear);
        uiModel.addAttribute("minyear",minyear);

        ///////////////////////////////////////////Enroll
        List enrollTypeItems = new ArrayList();
        enrollTypeItems.add("Listed");
        enrollTypeItems.add("Non-Listed");
        uiModel.addAttribute("enrollTypeItems",enrollTypeItems);

        ///////////////////////////////////////////Send Address
        List sendAddressItems = new ArrayList();
        sendAddressItems.add("--โปรดเลือก--");
        sendAddressItems.add("ที่ทำงาน");
        sendAddressItems.add("บ้าน");
        uiModel.addAttribute("sendAddressItems",sendAddressItems);
    }

    private void addSelectedItem2(Model uiModel){
        ///////////////////////////////////////////
        List marriagedStatusItems = new ArrayList();
        marriagedStatusItems.add("โสด");
        marriagedStatusItems.add("สมรส");
        marriagedStatusItems.add("หย่าร้าง");
        uiModel.addAttribute("marriagedStatusItems",marriagedStatusItems);

        //////////////////////////////////////////SpouseYear

        int maxyearSpouse = new Date().getYear()+1900-20;
        int minyearSpouse = maxyearSpouse-80;
        uiModel.addAttribute("maxyearSpouse",maxyearSpouse);
        uiModel.addAttribute("minyearSpouse",minyearSpouse);

        //////////////////////////////////////////Year

        int maxyear = new Date().getYear()+1900;
        int minyear = maxyear-60;
        uiModel.addAttribute("maxyear",maxyear);
        uiModel.addAttribute("minyear",minyear);
    }

    private void addSelectedItem5(Model uiModel){
        List golferItems = new ArrayList();
        golferItems.add("--โปรดเลือก--");
        golferItems.add("นักกอล์ฟ");
        golferItems.add("ไม่ใช่นักกอล์ฟ");
        uiModel.addAttribute("golferItems",golferItems);

        List smokingItems = new ArrayList();
        smokingItems.add("--โปรดเลือก--");
        smokingItems.add("สูบบุหรี่");
        smokingItems.add("ไม่สูบบุหรี่");
        uiModel.addAttribute("smokingItems",smokingItems);

        List jacketSizeItems = new ArrayList();
        jacketSizeItems.add("--โปรดเลือก--");
        jacketSizeItems.add("SS รอบอก 42 นิ้ว");
        jacketSizeItems.add("S รอบอก 44 นิ้ว");
        jacketSizeItems.add("M รอบอก 46 นิ้ว");
        jacketSizeItems.add("L รอบอก 48 นิ้ว");
        jacketSizeItems.add("XL รอบอก 50 นิ้ว");
        jacketSizeItems.add("XXL รอบอก 52 นิ้ว");
        jacketSizeItems.add("XXXL รอบอก 54 นิ้ว");
        uiModel.addAttribute("jacketSizeItems",jacketSizeItems);

        List jacketPoloItems = new ArrayList();
        jacketPoloItems.add("--โปรดเลือก--");
        /*jacketPoloItems.add("หญิง S รอบอก 34 นิ้ว");
        jacketPoloItems.add("หญิง M รอบอก 36 นิ้ว");
        jacketPoloItems.add("หญิง L รอบอก 38 นิ้ว");
        jacketPoloItems.add("หญิง XL รอบอก 40 นิ้ว");
        jacketPoloItems.add("หญิง XXL รอบอก 42 นิ้ว");
        jacketPoloItems.add("ชาย S รอบอก 38 นิ้ว");
        jacketPoloItems.add("ชาย M รอบอก 40 นิ้ว");
        jacketPoloItems.add("ชาย L รอบอก 42 นิ้ว");
        jacketPoloItems.add("ชาย XL รอบอก 44 นิ้ว");
        jacketPoloItems.add("ชาย XXL รอบอก 46 นิ้ว");
        jacketPoloItems.add("ชาย XXXL รอบอก 48 นิ้ว");*/
        jacketPoloItems.add("SSS รอบอก 34 นิ้ว");
        jacketPoloItems.add("SS รอบอก 36 นิ้ว");
        jacketPoloItems.add("S รอบอก 38 นิ้ว");
        jacketPoloItems.add("M รอบอก 40 นิ้ว");
        jacketPoloItems.add("L รอบอก 42 นิ้ว");
        jacketPoloItems.add("XL รอบอก 44 นิ้ว");
        jacketPoloItems.add("XXL รอบอก 46 นิ้ว");
        jacketPoloItems.add("XXXL รอบอก 48 นิ้ว");
        uiModel.addAttribute("jacketPoloItems",jacketPoloItems);

    }

    private void addEducationDegreeList(Model uiModel){
        List educationDegree = new ArrayList();
        educationDegree.add(new EducationDegree("Bachelor","ปริญญาตรี"));
        educationDegree.add(new EducationDegree("Master","ปริญญาโท"));
        educationDegree.add(new EducationDegree("PhD","ปริญญาเอก"));

        uiModel.addAttribute("educationDegree",educationDegree);
    }


    private List findEducationProfileList(String degree,Long id){
        List educationList = new ArrayList();
        List allEdList = Education_profile.findAllEducation_profiles();
        for(int i=0;i<allEdList.size();i++)
        {
            Education_profile x = (Education_profile)allEdList.get(i);
            if(x.getDegree().equals(degree)&&x.getStudentProfile().getId().equals(id)){
              educationList.add(x);
            }
        }
        return educationList;
    }

    private List findTrainingProfileList(Long id){
        List trainingList = new ArrayList();
        List allTrainList = Training_profile.findAllTraining_profiles();
        for(int i=0;i<allTrainList.size();i++)
        {
            Training_profile x = (Training_profile)allTrainList.get(i);
            if(x.getStudentProfile().getId().equals(id)){
                trainingList.add(x);
            }
        }
        return trainingList;
    }

    public static List findChildrenProfileList(Long id){
        List childrenList = new ArrayList();
        List allChildrenList = Children_profile.findAllChildren_profiles();
        //System.out.println(">>>>>findChildrenProfileList: id "+id);
        for(int i=0;i<allChildrenList.size();i++)
        {
            Children_profile x = (Children_profile)allChildrenList.get(i);
            //System.out.println(">>>>>findChildrenProfileList: profile_id of children "+x.getStudentProfile().getId());
            if(x.getStudentProfile().getId().equals(id)){
                childrenList.add(x);
            }
        }
        return childrenList;
    }

    @RequestMapping(value = "/actionChangeClass",method = RequestMethod.POST,produces = "text/html")
    public String actionChangeClass(HttpServletRequest request){
        Long class_id = Long.parseLong(request.getParameter("cmaClass"));
        String dataState = request.getParameter("dataState");
        return "redirect:/std_profiles?cmaClass="+class_id+"&dataState="+dataState;
    }

    //
    //  Copy from roo controller for solve the auto delete roo controller problem
    //
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Student std_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, std_profile);
            return "std_profiles/create";
        }
        uiModel.asMap().clear();
        std_profile.persist();
        return "redirect:/std_profiles/" + encodeUrlPathSegment(std_profile.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Student());
        addEducationDegreeList(uiModel);
        addSelectedItem1(uiModel);
        addSelectedItem2(uiModel);
        addSelectedItem5(uiModel);
        return "std_profiles/create";
    }

    @RequestMapping(value = "/{id}", params = "print" , produces = "text/html")
    public String print(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("student", Student.findStudent(id));
        uiModel.addAttribute("itemId", id);
        List childrenList = findChildrenProfileList(id);
        uiModel.addAttribute("children_profile",childrenList);
        List bdList = findEducationProfileList("Bachelor",id);
        List mdList = findEducationProfileList("Master",id);
        List phdList = findEducationProfileList("PhD",id);
        List trainingList = findTrainingProfileList(id);
        uiModel.addAttribute("education_profile_bd",bdList);
        uiModel.addAttribute("education_profile_md",mdList);
        uiModel.addAttribute("education_profile_phd",phdList);
        uiModel.addAttribute("training_profile",trainingList);
        return "std_profiles/print";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("student", Student.findStudent(id));
        uiModel.addAttribute("itemId", id);
        List childrenList = findChildrenProfileList(id);
        uiModel.addAttribute("children_profile",childrenList);
        List bdList = findEducationProfileList("Bachelor",id);
        List mdList = findEducationProfileList("Master",id);
        List phdList = findEducationProfileList("PhD",id);
        List trainingList = findTrainingProfileList(id);
        uiModel.addAttribute("education_profile_bd",bdList);
        uiModel.addAttribute("education_profile_md",mdList);
        uiModel.addAttribute("education_profile_phd",phdList);
        uiModel.addAttribute("training_profile",trainingList);
        return "std_profiles/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@ModelAttribute("warning_message")String warning_message,@ModelAttribute("message") String message,@RequestParam(value = "cmaClass", required = false) Long cmaClass, Model uiModel,HttpServletRequest httpServletRequest) {
        List cmaClassList = Batch.findAllBatches();
        String dataState = httpServletRequest.getParameter("dataState");

        log.info("list() | dataState : "+dataState);

        if(dataState == null || dataState.equals("") || dataState.equalsIgnoreCase(StudentDataState.INIT)){
            dataState = StudentDataState.INIT;
        }else if(dataState.equalsIgnoreCase(StudentDataState.REVISED)){
            dataState = StudentDataState.REVISED;
        }else if(dataState.equalsIgnoreCase(StudentDataState.UPTODATE)){
            dataState = StudentDataState.UPTODATE;
        }

        if(cmaClass==null){
            if(cmaClassList.size()==0){
                uiModel.addAttribute("classList",cmaClassList);
                return "std_profiles/list";
            }
            else{
                Batch first = Batch.findAllBatches().get(0);
                cmaClass = first.getId();
            }
        }
        /*System.out.println(">>>>>>>>>class_id" + cmaClass);*/

        if(warning_message!=null&&warning_message.equals("noselect")){
            uiModel.addAttribute("warning_message","กรุณาเลือกก่อนส่ง");
        }
        if(message!=null){
            if(message.equals("complete"))
              uiModel.addAttribute("message","Email ถูกส่งเรียบร้อยแล้ว");
            else uiModel.addAttribute("message",message);
        }

        log.info("list() | batch id : "+cmaClass);
        Batch batch = Batch.findBatch(cmaClass);
        List std_profiles = Student.listStudent(dataState,batch);

        //System.out.println(std_profiles.get(0));

        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("classList",cmaClassList);
        uiModel.addAttribute("std_profiles", std_profiles);
        uiModel.addAttribute("class_id",cmaClass);
        uiModel.addAttribute("dataState",dataState);
        return "std_profiles/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Student std_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, std_profile);
            return "std_profiles/update";
        }
        uiModel.asMap().clear();
        std_profile.merge();
        return "redirect:/std_profiles/" + encodeUrlPathSegment(std_profile.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Student.findStudent(id));
        return "std_profiles/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Student student = Student.findStudent(id);
        if(student.getDataState().equals(StudentDataState.INIT)){
            MapStudent mapStudent = MapStudent.findMapStudent(student, null, null);
            mapStudent.setInitStudent(null);
            mapStudent.merge();

            boolean result = deleteRelatedProfiledeleteRelatedProfile(student);

            UserRegis userRegis = UserRegis.getUserRegis(student);
            if(userRegis != null){
                userRegis.remove();
            }
            log.info("delete() | delete init student id : "+student.getId());
            student.remove();
        }else if(student.getDataState().equals(StudentDataState.REVISED)){
            MapStudent mapStudent = MapStudent.findMapStudent(null, student, null);
            Student uptodateStudent = mapStudent.getUpToDateStudent();
            mapStudent.setRevisedStudent(null);
            mapStudent.setUpToDateStudent(null);
            mapStudent.merge();

            boolean result = deleteRelatedProfiledeleteRelatedProfile(student);
            result = deleteRelatedProfiledeleteRelatedProfile(uptodateStudent);

            student.getUserWeb().remove();
            uptodateStudent.getUserWeb().remove();

            log.info("delete() | delete revised student id : "+student.getId());
            student.remove();
            log.info("delete() | delete uptodate student id : "+uptodateStudent.getId());
            uptodateStudent.remove();
        }else if(student.getDataState().equals(StudentDataState.UPTODATE)){
            log.info("delete() | cannot delete uptodate student id : "+student.getId());
        }
        uiModel.asMap().clear();
        return "redirect:/std_profiles";
    }

    private boolean deleteRelatedProfiledeleteRelatedProfile(Student student){
        boolean result = true;
        if(student != null){
            AttachFile attachFile = AttachFile.getAttachFile(student);
            if(attachFile!=null){
                attachFile.remove();
            }

            List educationProfileList = Education_profile.listEducationProfile(student);
            if(educationProfileList != null){
                result = result && Education_profile.deleteEducationProfile(educationProfileList);

            }

            List trainingProfileList = Training_profile.listTrainingProfile(student);
            if(trainingProfileList != null){
                result = result && Training_profile.deleteTrainingProfile(trainingProfileList);
            }

            List childrenProfileList = Children_profile.listChildrenProfile((student));
            if(childrenProfileList != null){
                result = result && Children_profile.deleteChildrenProfile(childrenProfileList);
            }
        }else{
            result = false;
        }
        return result;
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        Locale locale = new Locale("th", "TH");
        uiModel.addAttribute("std_profile_birthdate_date_format", DateTimeFormat.patternForStyle("M-", locale));
        uiModel.addAttribute("std_profile_spousebirthday_date_format", DateTimeFormat.patternForStyle("M-", locale));
        uiModel.addAttribute("std_profile_children1birthday_date_format", DateTimeFormat.patternForStyle("M-", locale));
    }

    void populateEditForm(Model uiModel, Student student) {
        uiModel.addAttribute("student", student);
        addDateTimeFormatPatterns(uiModel);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

}
