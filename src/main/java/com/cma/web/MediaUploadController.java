package com.cma.web;

import com.cma.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.*;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.cma.common.authenManager.genPassword;
import static com.cma.common.authenManager.sha256;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@RequestMapping("/mediauploads")
@Controller
@RooWebScaffold(path = "mediauploads", formBackingObject = MediaUpload.class)
public class MediaUploadController {
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private DateFormat dMyFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("th","TH"));
    private DateFormat passwordFormat = new SimpleDateFormat("ddMMMyyyy",Locale.US);

    private static Log log = LogFactory.getLog(MediaUploadController.class);

    private String checkDate(Date date){
        if(date==null){
            return "-";
        }

        return df.format(date);
    }

    private String checkTel(String text){
        if(text.contains("null")||text.contains("--")){
            return "-";
        }
        return text;
    }

    private String checkTel(String text,String text2,String text3){
        if(text.contains("null")||text.contains("--"))return "-";
        else if(text2==null||text2.equals("")){
            text2="-";
        }
        else if(text3==null||text3.equals("")){
            text3="-";
        }
        return text+" ถึง "+text2+" ต่อ "+text3;
    }

    private String checkText(String text,String fronttext){

        if(text==null||text.equals(""))
            {
                return "-";
            }
        return fronttext+text;
    }

    private String checkText(String text){
        /*System.out.println(">>>>"+text+"<<<<<"+text.equals("\"null\""));*/
        if(text==null||text.equals(""))return "-";
        else if(text.equals("true"))return "YES";
        else if(text.equals("false"))return "NO";
        else return text;
    }

    private String convertBooleanToString(Boolean value){
        String text = "-";
        if(value == null){
            text = "-";
        }else if(value){
            text = "YES";
        }else{
            text = "NO";
        }
        return text;
    }

    private Boolean convertStringToBoolean(String text){
        Boolean value = Boolean.FALSE;
        if(text.equalsIgnoreCase("YES")){
            value = Boolean.TRUE;
        }

        return  value;
    }

    private String countByField(Batch batch,String fieldName, String dataState){
        EntityManager stdProfilleEntityManager = Student.entityManager();
        EntityManager userloginEntityManager = UserRegis.entityManager();
        Query query;
        if(fieldName.equals("submit")){
           query = stdProfilleEntityManager.createQuery("select count(x.id) from Student x where studentClass=:std_class and x.submitProfile=true and x.dataState= :dataState");
        }
        else if(fieldName.equals("role_user")){
           query = userloginEntityManager.createQuery("select count(x.id) from UserRegis x where x.studentProfile.studentClass=:std_class and x.userRole.role_name='ROLE_USER' and x.studentProfile.dataState= :dataState");
        }
        else {
           query = userloginEntityManager.createQuery("select count(x.id) from UserRegis x where x.studentProfile.studentClass=:std_class and x.userRole.role_name='ROLE_USER_UNCHANGE' and x.studentProfile.dataState= :dataState");
        }
        query.setParameter("std_class", batch);
        query.setParameter("dataState", dataState);
        List resultList = query.getResultList();
        if(resultList.get(0)!=null)
            return resultList.get(0)+"";
        return "0";
    }


    @RequestMapping(value = "/submitReport")
    public void submitReport(HttpServletRequest request,Model uiModel, HttpServletResponse response){
        Long classId = Long.parseLong(request.getParameter("cmaClass"));
        String dataState = request.getParameter("dataState");

        if(dataState == null || dataState.equals("") || dataState.equalsIgnoreCase(StudentDataState.INIT)){
            dataState = StudentDataState.INIT;
        } else if(dataState.equalsIgnoreCase(StudentDataState.REVISED)){
            dataState = StudentDataState.REVISED;
        } else if(dataState.equalsIgnoreCase(StudentDataState.UPTODATE)){
            dataState = StudentDataState.UPTODATE;
        }

        Batch batch = Batch.findBatch(classId);
        List<Student> studentList = Student.listStudent(dataState, batch);
        String fileName = batch.getNameEn()+"_report_"+dataState+".xls";

        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("report");
            Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
            data.put(0, new Object[]{
                    "TitleTh", "FirstnameTh", "LastnameTh", "UploadPhoto", "UploadIdcard", "UploadPassport",
                    "UploadOfficialPassport", "UploadApec", "UploadSlip1", "UploadSlip2"
            }); // table header

            Iterator it = studentList.iterator();
            Student student;
            AttachFile attachFile;
            int rowIndex = 1;
            while (it.hasNext()){
                student = (Student)it.next();
                attachFile = student.getAttachFile();
                data.put(rowIndex++, new Object[]{
                        student.getTitleTh(), student.getFirstnameTh(), student.getLastnameTh(),
                        attachFile != null && attachFile.getUploadPhoto() != null ? attachFile.getUploadPhoto() : "",
                        attachFile != null && attachFile.getUploadIdcard() != null ? attachFile.getUploadIdcard() : "",
                        attachFile != null && attachFile.getUploadPassport() != null ? attachFile.getUploadPassport() : "",
                        attachFile != null && attachFile.getUploadPf() != null ? attachFile.getUploadPf() : "",
                        attachFile != null && attachFile.getUploadApec() != null ? attachFile.getUploadApec() : "",
                        attachFile != null && attachFile.getUploadSlip1() != null ? attachFile.getUploadSlip1() : "",
                        attachFile != null && attachFile.getUploadSlip2() != null ? attachFile.getUploadSlip2() : ""
                });
            }

            System.out.println("studentList : "+studentList.size());

            data.put(rowIndex++, new Object[]{ "Summary","","","","","","","","",""});
            data.put(rowIndex++, new Object[]{ "CMA#", batch.getNameTh(),"","","","","","","",""});
            data.put(rowIndex++, new Object[]{ "Number of students", studentList != null ? studentList.size() : 0,"","","","","","","",""});
            data.put(rowIndex++, new Object[]{ "Login", countByField(batch, "role_user",dataState),"","","","","","","",""});
            data.put(rowIndex++, new Object[]{ "Unlogin", countByField(batch, "role_user_unchange",dataState),"","","","","","","",""});
            data.put(rowIndex++, new Object[]{ "Submit Profile", countByField(batch,"submit",dataState),"","","","","","","",""});

            Set<Integer> keyset = data.keySet();
            int rownum = 0;
            for (Integer key : keyset) {
                System.out.println("rownum : "+rownum);
                Row row = sheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Boolean) {
                        Boolean value = (Boolean) obj;
                        String text = convertBooleanToString(value);
                        cell.setCellValue(text);
                    } else if (obj instanceof String){
                        cell.setCellValue((String) obj);
                    }else if(obj instanceof Double){
                        cell.setCellValue((Double)obj);
                    }

                }
            }

            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;Filename=\""+fileName+"\"");

            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        } catch (Exception e){
            e.printStackTrace();
        }




    }

    @Transactional
    @RequestMapping(value = "/submitextendExport", produces = "text/html")
    public  void submitextendExport(HttpServletRequest request,Model uiModel, HttpServletResponse response){
        Long classId = Long.parseLong(request.getParameter("cmaClass"));
        String dataState = request.getParameter("dataState");
        String type = request.getParameter("type");

        Batch batch = Batch.findBatch(classId);

        if(dataState == null || dataState.equals("") || dataState.equalsIgnoreCase(StudentDataState.INIT)){
            dataState = StudentDataState.INIT;
        } else if(dataState.equalsIgnoreCase(StudentDataState.REVISED)){
            dataState = StudentDataState.REVISED;
        } else if(dataState.equalsIgnoreCase(StudentDataState.UPTODATE)){
            dataState = StudentDataState.UPTODATE;
        }

        String fileName = null;
        if(type.equals(Constant.REPORT_CHILDREN)){
            fileName = batch.getNameEn()+"_childrenProfileExport_"+dataState+".xls";
        }else if(type.equals(Constant.REPORT_EDUCATION)){
            fileName = batch.getNameEn()+"_educationExport_"+dataState+".xls";
        }else if(type.equals(Constant.REPORT_TRAINING)){
            fileName = batch.getNameEn()+"_trainingExport_"+dataState+".xls";
        }

        List<Student> studentList = Student.listStudent(dataState, batch);

        String[] headerChildrenReport = {"FirstNameTh", "LastnameTh", "ChildrenName", "ChildrenLastName", "ChildrenCareer", "ChildrenBirthdate", "ChildrenRace",
                                        "ChildrenNationality", "ChildrenReligion"};
        String[] headerEducationReport = {"FirstNameTh", "LastnameTh", "Degree", "Field", "University", "GraduateYear"};
        String[] headerTrainingReport = {"FirstNameTh", "LastnameTh", "trainingName", "trainingInstitute", "trainingClass", "trainingYear"};

        Map<Integer, String[]> headerMap = new HashMap<Integer, String[]>();
        headerMap.put(0,headerChildrenReport);
        headerMap.put(1, headerEducationReport);
        headerMap.put(2, headerTrainingReport);

        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("report");
            Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
            data.put(0, headerMap.get(Integer.valueOf(type))); // table header
            Iterator it = studentList.iterator();

            if(type.equals(Constant.REPORT_CHILDREN)){
                int rowIndex = 1;
                while(it.hasNext()) {
                    Student student = (Student) it.next();
                    Iterator childIt = student.getChildren_profileSet().iterator();
                    Object[] childInfo ;
                    if(!childIt.hasNext()){
                        childInfo = new Object[]{student.getFirstnameTh(), student.getLastnameTh(), "", "", "", "", "", "", ""};
                        data.put(rowIndex, childInfo);
                        rowIndex ++;
                    }else{
                        int childIndex = 0;
                        while(childIt.hasNext()) {
                            Children_profile children_profile = (Children_profile) childIt.next();
                            if(childIndex == 0){
                                childInfo = new Object[]{student.getFirstnameTh(), student.getLastnameTh(), children_profile.getFirstName(),
                                        children_profile.getLastName(), children_profile.getCareer(), children_profile.getBirthday() != null ? df.format(children_profile.getBirthday()) : "", children_profile.getRace(),
                                        children_profile.getNationality(), children_profile.getReligion()};
                            }else{
                                childInfo = new Object[]{"", "", children_profile.getFirstName(),
                                        children_profile.getLastName(), children_profile.getCareer(), children_profile.getBirthday() != null ? df.format(children_profile.getBirthday()) : "", children_profile.getRace(),
                                        children_profile.getNationality(), children_profile.getReligion()};
                            }
                            data.put(rowIndex, childInfo);
                            rowIndex ++;
                            childIndex++;
                        }
                    }
                }
            }else if(type.equals(Constant.REPORT_EDUCATION)){
                int rowIndex =1;
                while(it.hasNext()) {
                    Student student = (Student) it.next();
                    Iterator educationIt = student.getEducation_profileSet().iterator();
                    Object[] educationinfo ;
                    if(!educationIt.hasNext()){
                        educationinfo = new Object[]{student.getFirstnameTh(), student.getLastnameTh(), "", "", "", ""};
                        data.put(rowIndex, educationinfo);
                        rowIndex ++;
                    }else{
                        int educationIndex = 0;
                        while(educationIt.hasNext()) {
                            Education_profile education_profile = (Education_profile) educationIt.next();
                            if(educationIndex == 0){
                                educationinfo = new Object[]{student.getFirstnameTh(), student.getLastnameTh(),education_profile.getDegree(),
                                education_profile.getField(), education_profile.getUniversity(), education_profile.getGraduateYear()};
                            }else{
                                educationinfo = new Object[]{"", "",education_profile.getDegree(),
                                        education_profile.getField(), education_profile.getUniversity(), education_profile.getGraduateYear()};
                            }
                            data.put(rowIndex, educationinfo);
                            rowIndex ++;
                            educationIndex++;
                        }
                    }
                }
            }else if(type.equals(Constant.REPORT_TRAINING)){
                int rowIndex =1;
                while(it.hasNext()) {
                    Student student = (Student) it.next();
                    Iterator trainingIt = student.getTraining_profileSet().iterator();
                    Object[] trainingInfo ;
                    if(!trainingIt.hasNext()){
                        trainingInfo = new Object[]{student.getFirstnameTh(), student.getLastnameTh(), "", "", "", ""};
                        data.put(rowIndex, trainingInfo);
                        rowIndex ++;
                    }else{
                        int trainingIndex = 0;
                        while(trainingIt.hasNext()) {
                            Training_profile training_profile = (Training_profile) trainingIt.next();
                            if(trainingIndex == 0){
                                trainingInfo = new Object[]{student.getFirstnameTh(), student.getLastnameTh(),training_profile.getTrainingName(),
                                training_profile.getTrainingInstitute(), training_profile.getTrainingClass(), training_profile.getTrainingYear()};
                            }else{
                                trainingInfo = new Object[]{"", "", student.getLastnameTh(),training_profile.getTrainingName(),
                                        training_profile.getTrainingInstitute(), training_profile.getTrainingClass(), training_profile.getTrainingYear()};
                            }
                            data.put(rowIndex, trainingInfo);
                            rowIndex ++;
                            trainingIndex++;
                        }
                    }
                }
            }

            Set<Integer> keyset = data.keySet();
            int rownum = 0;
            for (Integer key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Boolean) {
                        Boolean value = (Boolean) obj;
                        String text = convertBooleanToString(value);
                        cell.setCellValue(text);
                    } else if (obj instanceof String){
                        cell.setCellValue((String) obj);
                    }else if(obj instanceof Double){
                        cell.setCellValue((Double)obj);
                    }

                }
            }

            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;Filename=\""+fileName+"\"");

            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @RequestMapping(params = "exportReport", produces = "text/html")
    public  String reportForm(Model uiModel, HttpServletRequest httpServletRequest){
        List classList = Batch.findAllBatches();
        String dataState = httpServletRequest.getParameter("dataState");
        uiModel.addAttribute("classList",classList);
        uiModel.addAttribute("dataState",dataState);
        return "mediauploads/exportReport";
    }

    @RequestMapping(params = "extendExport", produces = "text/html")
    public  String extendExportForm(Model uiModel,HttpServletRequest httpServletRequest){
        List classList = Batch.findAllBatches();
        String dataState = httpServletRequest.getParameter("dataState");
        uiModel.addAttribute("classList",classList);
        uiModel.addAttribute("dataState",dataState);
        List typeList = new ArrayList();
        typeList.add("ประวัติบุตร/ธิดา");
        typeList.add("การศึกษา");
        typeList.add("การฝึกอบรม");
        uiModel.addAttribute("typeList",typeList);
        return "mediauploads/extendExport";
    }

    @RequestMapping(value = "/submitExport")
    public void submitExport(HttpServletRequest request,Model uiModel, HttpServletResponse response){
        Long classId = Long.parseLong(request.getParameter("cmaClass"));
        String dataState = request.getParameter("dataState");

        log.info("submitExport() | batch : "+classId +" | dataState : "+dataState);

        if(dataState == null || dataState.equals("") || dataState.equalsIgnoreCase(StudentDataState.INIT)){
            dataState = StudentDataState.INIT;
        } else if(dataState.equalsIgnoreCase(StudentDataState.REVISED)){
            dataState = StudentDataState.REVISED;
        } else if(dataState.equalsIgnoreCase(StudentDataState.UPTODATE)){
            dataState = StudentDataState.UPTODATE;
        }

        Batch batch = Batch.findBatch(classId);
        List<Student> studentList = Student.listStudent(dataState, batch);
        String fileName = batch.getNameEn()+"_export_"+dataState+".xls";

        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("report");
            Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
            data.put(0, new Object[] {
                    "Student Id", "TitleTh", "คำนำหน้า (ไม่เป็นทางการ)", "FirstnameTh", "LastnameTh", "FullNameTh",
                    "TitleEn", "FirstNameEn", "LastnameEn", "FullNameEn", "Nickname", "Sex", "Birthdate",
                    "IdcardNo", "PassportNo", "RopNo", "PositionTh", "PositionEn", "InstitutionTh", "InstitutionEn",
                    "InstitutionNo", "InstitutionMooNo", "InstitutionBuilding", "InstitutionFloor", "InstitutionSoi",
                    "InstitutionStreet", "InstitutionSubdistrict", "InstitutionDistrict", "InstitutionProvince",
                    "InstitutionPostalCode", "FullWorkAddress", "Worktel1", "Worktel2", "Workfax", "Mobile1", "Mobile2",
                    "Email", "Line Id","Facebook", "HomeNo", "MooNo", "Building", "Village",
                    "Floor", "Soi", "Street", "Subdistrict", "District",
                    "Province", "PostalCode", "FullHomeAddress", "Hometel1", "Hometel2", "Homefax",
                    "SendingAddress", "MarriedStatus", "SpouseFirstName", "SpouseLastName", "SpouseCareer",
                    "SpouseInstitution", "SpouseBirthDay", "SpouseRace", "SpouseNationality", "SpouseReligion",
                    "CollboratorFirstName", "CollboratorLastName", "CollboratorFullName", "CollboratorTel", "CollboratorMobile", "CollboratorFax",
                    "CollboratorEmail", "CollboratorLineId", "Driver Tel", "CarNo", "CarBrand",
                    "CarColor", "PlayGolf", "HandyCap", "GeneralFood", "HalalFood",
                    "JFood", "VegeterianFood", "AllergiesSeaFood", "BeefFood", "OtherFood",
                    "Smoking", "JacketSize", "PoloSize", "Waist", "Tall",
                    "ReceiptDetailName", "ReceiptDetailAddress", "TaxId", "SubmitProfile", "Group"
            });

            Iterator it = studentList.iterator();
            int rowIndex = 1;
            while(it.hasNext()) {
                Student student = (Student) it.next();
                String workTel = checkTel(student.getFrontworktel1()+"-"+student.getMiddleworktel1()+"-"+student.getLastworktel1(),student.getWorktel1_2(),student.getExtworktel1());
                String workTel2 = checkTel(student.getFrontworktel2()+"-"+student.getMiddleworktel2()+"-"+student.getLastworktel2(),student.getWorktel2_2(),student.getExtworktel2());
                String workFax = checkTel(student.getFrontworkfax()+"-"+student.getMiddleworkfax()+"-"+student.getLastworkfax());
                String mobile1 = checkTel(student.getFrontmobile1()+"-"+student.getMiddlemobile1()+"-"+student.getLastmobile1());
                String mobile2 = checkTel(student.getFrontmobile2()+"-"+student.getMiddlemobile2()+"-"+student.getLastmobile2());
                String homeTel1 = checkTel(student.getFronthometel1()+"-"+student.getMiddlehometel1()+"-"+student.getLasthometel1(),student.getHometel1_2(),student.getExthometel1());
                String homeTel2 = checkTel(student.getFronthometel2()+"-"+student.getMiddlehometel2()+"-"+student.getLasthometel2(),student.getHometel2_2(),student.getExthometel2());
                String homeFax = checkTel(student.getFronthomefax()+"-"+student.getMiddlehomefax()+"-"+student.getLasthomefax());
                String collaboratorTel = checkTel(student.getFrontcollboratorTel()+"-"+student.getMiddlecollboratorTel()+"-"+student.getLastcollboratorTel(),student.getCollboratorTel_2(),student.getExtcollboratorTel());
                String collaboratorMobile = checkTel(student.getFrontcollboratorMobile()+"-"+student.getMiddlecollboratorMobile()+"-"+student.getLastcollboratorMobile());
                String collaboratorFax = checkTel(student.getFrontcollboratorFax()+"-"+student.getMiddlecollboratorFax()+"-"+student.getLastcollboratorFax());

                String receiptDetailAddress = student.getReceiptDetailAddress();
                if (receiptDetailAddress != null) {
                    receiptDetailAddress = receiptDetailAddress.replaceAll("\r\n", " ");
                }
                data.put(rowIndex,new Object[] {
                        String.valueOf(student.getId()), student.getTitleTh(), "", student.getFirstnameTh(), student.getLastnameTh(), student.getFullNameTh(),
                        student.getTitleEn(),student.getFirstnameEn(), student.getLastnameEn(), student.getFullNameEn(), student.getNickname(), student.getSex(), student.getBirthdate() != null ? df.format(student.getBirthdate()):"",
                        student.getIdcardNo(), student.getPassportNo(), student.getRopNo(), student.getPositionTh(),
                        student.getPositionEn(), student.getInstitutionTh(), student.getInstitutionEn(), student.getInstitutionNo(), student.getInstitutionMooNo(),
                        student.getInstitutionBuilding(), student.getInstitutionFloor(), student.getInstitutionSoi(), student.getInstitutionStreet(), student.getInstitutionSubdistrict(),
                        student.getInstitutionDistrict(), student.getInstitutionProvince(), student.getInstitutionPostalCode(), student.getWorkFullAddress(),
                        workTel, workTel2, workFax, mobile1, mobile2, student.getEmail(),
                        student.getLineId(), student.getFacebook(), student.getHomeNo(), student.getMooNo(), student.getBuilding(),
                        student.getVillage(),student.getFloor(), student.getSoi(), student.getStreet(), student.getSubdistrict(),
                        student.getDistrict(), student.getProvince(), student.getPostalCode(), student.getHomeFullAddress(), homeTel1, homeTel2, homeFax,
                        student.getSendingAddress(), student.getMarriedStatus(), student.getSpouseFirstName(), student.getSpouseLastName(), student.getSpouseCareer(),
                        student.getSpouseInstitution(), student.getSpouseBirthDay() != null ? df.format(student.getSpouseBirthDay()) : "", student.getSpouseRace(), student.getSpouseNationality(), student.getSpouseReligion(),
                        student.getCollboratorFirstName(), student.getCollboratorLastName(), student.getCollboratorFullName(), collaboratorTel, collaboratorMobile, collaboratorFax,
                        student.getCollboratorEmail(), student.getCollboratorLineId(), student.getDriverTel(), student.getCarNo(), student.getCarBrand(),
                        student.getCarColor(), student.getPlayGolf(), student.getHandyCap(), student.isGeneralFood(), student.isHalalFood(),
                        student.isJFood(), student.isVegeterianFood(), student.isAllergiesSeaFood(), student.isBeefFood(), student.getOtherFood(),
                        student.getSmoking(), student.getJacketSize(), student.getPoloSize(), student.getWaist(), student.getTall(),
                        student.getReceiptDetailName(), receiptDetailAddress, student.getTaxId(), student.getSubmitProfile(), student.getGroupName()
                });
                rowIndex++;
            }

            // style for read-only column
            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);

            Set<Integer> keyset = data.keySet();
            int rownum = 0;
            for (Integer key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum);
                    if(cellnum == 0){
                        cell.setCellStyle(unlockedCellStyle);
                    }
                    cellnum ++;
                    if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Boolean) {
                        Boolean value = (Boolean) obj;
                        String text = convertBooleanToString(value);
                        cell.setCellValue(text);
                    } else if (obj instanceof String){
                        cell.setCellValue((String) obj);
                    }else if(obj instanceof Double){
                        cell.setCellValue((Double)obj);
                    }else{
                        cell.setCellValue("");
                    }

                }
            }

            OutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;Filename=\""+fileName+"\"");

            workbook.write(out);
            out.close();
            log.info("Excel written successfully..");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "exportAllProfile", produces = "text/html")
    public  String exportExcel(Model uiModel, HttpServletRequest httpServletRequest){
        List classList = Batch.findAllBatches();
        String dataState = httpServletRequest.getParameter("dataState");
        uiModel.addAttribute("classList",classList);
        uiModel.addAttribute("dataState",dataState);
        return "mediauploads/exportExcel";
    }

    @RequestMapping(params = "buildForm", produces = "text/html")
    public String buildForm(Model uiModel , @RequestParam("dataState") String dataState) {
        populateEditForm(uiModel, new MediaUpload());
        List classList = Batch.findAllBatches();
        String templateLink = "./mediauploads/downloadFile?fileName=init_template.xls";
        if(dataState.equalsIgnoreCase(StudentDataState.REVISED) ||dataState.equalsIgnoreCase(StudentDataState.UPTODATE )){
            if(classList != null){
                templateLink = "./mediauploads/submitExport?cmaClass="+((Batch)classList.get(0)).getId()+"&dataState="+dataState.toLowerCase();
            }
        }
        log.info("templateLink : "+templateLink);
        uiModel.addAttribute("templateLink",templateLink);
        uiModel.addAttribute("classList",classList);
        uiModel.addAttribute("dataState",dataState.toLowerCase());
        return "mediauploads/create";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid MediaUpload mediaUpload, BindingResult bindingResult, Model uiModel, @RequestParam("content") CommonsMultipartFile content, HttpServletRequest httpServletRequest) {

        boolean result = false;
        String message =null;
        String dataState = httpServletRequest.getParameter("dataState");
        try {
            Long class_id = Long.parseLong(httpServletRequest.getParameter("cmaClass"));
            log.info("create() | dataState : " + dataState);
            Batch batch = Batch.findBatch(class_id);

            /*System.out.println(">>>>>>>>>>>>>>>>>class id" + class_id);*/
            InputStream inputStream = content.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter objDefaultFormat = new DataFormatter();
            FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
            //Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowIndex = row.getRowNum(); //skip header
                ArrayList<String> studentData = null;
                if(rowIndex == 0){
                    continue;
                }else{
                    //For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    studentData = new ArrayList<String>();

                    for(int r=0; r< row.getLastCellNum() ; r++){
                        Cell cell = row.getCell(r, Row.RETURN_NULL_AND_BLANK); // including null cell
                        objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of cell will return string value
                        String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
                        studentData.add(cellValueStr);
                    }

                    if(dataState.trim().equalsIgnoreCase(StudentDataState.INIT)){
                        result = createInitData(studentData,batch);
                    }else if(dataState.trim().equalsIgnoreCase(StudentDataState.REVISED)){
                        result = createRevisedData(studentData,batch);
                    } else if(dataState.trim().equalsIgnoreCase(StudentDataState.UPTODATE)){
                        result = updateUptodateData(studentData, batch);
                    }

                    log.info("create() | result : "+result);

                }
            }

            message = "Import รายชื่อนักเรียนเรียบร้อยแล้ว";

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }                         //------------------------

        populateEditForm(uiModel, new MediaUpload());
        List classList = Batch.findAllBatches();
        uiModel.addAttribute("dataState",dataState);
        uiModel.addAttribute("classList",classList);
        uiModel.addAttribute("message",message);
        return "mediauploads/create";
    }

    private boolean createInitData(ArrayList<String> studentData, Batch batch){
        boolean result = false;
        if(studentData != null){
            try {
                log.info("add student");
                Student student = new Student();
                student.setTitleTh(studentData.get(0));
                student.setFirstnameTh(studentData.get(1));
                student.setLastnameTh(studentData.get(2));
                student.setPositionTh(studentData.get(3));
                student.setInstitutionTh(studentData.get(4));
                student.setEmail(studentData.get(5).trim());
                student.setEmail2(studentData.get(6).trim());
                student.setSubmitProfile(false);
                student.setStudentClass(batch);
                student.setDataState(StudentDataState.INIT);
                student.persist();

                log.info("add userRegis");
                int maxUserId=getLastRecord(batch.getId());
                maxUserId ++;

                UserRegis userRegis = new UserRegis();
                userRegis.setEnabled(true);
                userRegis.setUsername(batch.getNameEn() + "_" + (String.format("%03d", maxUserId)));
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

                result = true;
                log.info("Successfully create init student");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }

    private boolean createRevisedData(ArrayList<String> studentData, Batch batch){
        boolean result = false;
        if(studentData != null){
            try {
                String studentIdStr = studentData.get(0);
                log.info("createRevisedData() | studentId : "+studentIdStr);
                if(studentIdStr == null || studentIdStr.trim().equals("")){
                    log.info("createRevisedData() | studentIdStr is blank => create new student");
                    Student revisedStudent = createRevisedStudentAndUpToDateStudent(batch, studentData, null);
                    log.info("createRevisedData() | Successfully create new student");
                    result = true;
                }else{
                    Long studentId = Long.valueOf(studentIdStr);
                    Student student = Student.findStudent(studentId);

                    log.info("createRevisedData() | batch : "+batch.getId());

                    if(student != null && student.getStudentClass().equals(batch)){
                        log.info("createRevisedData() | batch id : "+student.getStudentClass().getId());
                        log.info("createRevisedData() | student id : "+student.getId()+" | dataState : "+student.getDataState());
                        if(student.getDataState().equals(StudentDataState.INIT)){
                            MapStudent mapStudent = MapStudent.findMapStudent(student,null,null);
                            if(mapStudent != null){
                                Student revisedStudent = mapStudent.getRevisedStudent();
                                Student uptodateStudent = mapStudent.getUpToDateStudent();
                                if(revisedStudent == null){
                                    revisedStudent = createRevisedStudentAndUpToDateStudent(batch, studentData, student);
                                    mapStudent = MapStudent.findMapStudent(null,revisedStudent,null);
                                    uptodateStudent = mapStudent.getUpToDateStudent();

                                    if(student.getAttachFile() != null){
                                        AttachFile attachFile = student.getAttachFile();
                                        AttachFile revisedAttachFile = new AttachFile(attachFile);
                                        revisedAttachFile.setStdProfile(revisedStudent);
                                        revisedAttachFile.persist();

                                        AttachFile uptodateAttachFile = new AttachFile(attachFile);
                                        uptodateAttachFile.setStdProfile(uptodateStudent);
                                        uptodateAttachFile.persist();

                                    }

                                    if(student.getEducation_profileSet() != null){
                                        Set<Education_profile> educationSet = student.getEducation_profileSet();
                                        for(Education_profile edu : educationSet){
                                            Education_profile revisedEducationProfile = new Education_profile(edu);
                                            revisedEducationProfile.setStudentProfile(revisedStudent);
                                            revisedEducationProfile.persist();

                                            Education_profile uptodateEducationProfile = new Education_profile(edu);
                                            uptodateEducationProfile.setStudentProfile(uptodateStudent);
                                            uptodateEducationProfile.persist();
                                        }
                                    }

                                    if(student.getChildren_profileSet() != null){
                                        Set<Children_profile> childrenSet = student.getChildren_profileSet();
                                        for(Children_profile child : childrenSet){
                                            Children_profile revisedChildrenProfile = new Children_profile(child);
                                            revisedChildrenProfile.setStudentProfile(revisedStudent);
                                            revisedChildrenProfile.persist();

                                            Children_profile uptodateChildrenProfile = new Children_profile(child);
                                            uptodateChildrenProfile.setStudentProfile(uptodateStudent);
                                            uptodateChildrenProfile.persist();
                                        }
                                    }

                                    if(student.getTraining_profileSet() != null){
                                        Set<Training_profile> trainingSet = student.getTraining_profileSet();
                                        for(Training_profile training : trainingSet){
                                            Training_profile revisedTrainingProfile = new Training_profile(training);
                                            revisedTrainingProfile.setStudentProfile(revisedStudent);
                                            revisedTrainingProfile.persist();

                                            Training_profile uptodateTrainingProfile = new Training_profile(training);
                                            uptodateTrainingProfile.setStudentProfile(uptodateStudent);
                                            uptodateTrainingProfile.persist();
                                        }
                                    }

                                    log.info("createRevisedData() | create revised student : "+revisedStudent.getId());
                                } else {
                                    log.info("createRevisedData() | update revised student : "+revisedStudent.getId());
                                    revisedStudent = setStudent(studentData,revisedStudent);
                                    revisedStudent.merge();
                                }
                                result = true;
                            }else{
                                log.info("createRevisedData() | map student at init student id : "+studentId+" is not found");
                                result = false;
                            }

                        }else if(student.getDataState().equals(StudentDataState.REVISED)){
                            MapStudent mapStudent = MapStudent.findMapStudent(null,student,null);
                            if(mapStudent != null){
                                Student revisedStudent = mapStudent.getRevisedStudent();
                                Student uptodateStudent = mapStudent.getUpToDateStudent();
                                log.info("createRevisedData() | update revised student : "+revisedStudent.getId());
                                revisedStudent = setStudent(studentData,revisedStudent);
                                revisedStudent.merge();
                                result = true;
                            }else{
                                log.info("createRevisedData() | map student at revised student id : "+studentId+" is not found");
                                result = false;
                            }
                        }else{
                            log.info("createRevisedData() | student id : "+studentId+" is up to date data");
                            result = false;
                        }
                    }else{
                        log.info("createRevisedData() | student id : "+studentId+" is not found");
                        result = false;
                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            result = false;
        }

        return result;
    }

    private Student createRevisedStudentAndUpToDateStudent(Batch batch, ArrayList<String> studentData, Student initStudent) throws ParseException {
        Student revisedStudent = null;
        try{
            revisedStudent = new Student();
            revisedStudent = setStudent(studentData,revisedStudent);
            revisedStudent.setStudentClass(batch);
            revisedStudent.setDataState(StudentDataState.REVISED);
            revisedStudent.persist();

            Student uptodateStudent =new Student();
            uptodateStudent = setStudent(studentData,uptodateStudent);
            uptodateStudent.setStudentClass(batch);
            uptodateStudent.setDataState(StudentDataState.UPTODATE);
            uptodateStudent.persist();

            log.info("createRevisedStudentAndUpToDateStudent() | create userweb");
            UserWeb userWeb = new UserWeb();
            String username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+uptodateStudent.getFirstnameEn().toLowerCase();
            String password = batch.getCourse().getCode()+batch.getNumber();
            if(uptodateStudent.getBirthdate() != null){
                password = passwordFormat.format(uptodateStudent.getBirthdate());
            }

            userWeb.setUsername(generateUsername(uptodateStudent,batch));
            userWeb.setPassword(bCryptEncode(password));
            userWeb.persist();

            uptodateStudent.setUserWeb(userWeb);

            WebRole webRole = WebRole.getWebRole("ROLE_USER");
            if(webRole != null){
                // map user_web with role
                log.info("createRevisedStudentAndUpToDateStudent() | create userWebRole");
                UserWebRole userWebRole = new UserWebRole();
                userWebRole.setUserWeb(userWeb);
                userWebRole.setWebRole(webRole);
                userWebRole.persist();
            }

            log.info("createRevisedData() | update map student");
            MapStudent mapStudent = null;
            if(initStudent ==null){
                mapStudent = new MapStudent();
                mapStudent.setRevisedStudent(revisedStudent);
                mapStudent.setUpToDateStudent(uptodateStudent);
                mapStudent.persist();
            }else{
                mapStudent = MapStudent.findMapStudent(initStudent,null,null);
                if(mapStudent != null){
                    mapStudent.setRevisedStudent(revisedStudent);
                    mapStudent.setUpToDateStudent(uptodateStudent);
                    mapStudent.merge();
                }else{
                    mapStudent = new MapStudent();
                    mapStudent.setRevisedStudent(revisedStudent);
                    mapStudent.setUpToDateStudent(uptodateStudent);
                    mapStudent.persist();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  revisedStudent;
    }

    private boolean updateUptodateData(ArrayList<String> studentData, Batch batch){
        boolean result = false;
        if(studentData != null){
            try {
                Long studentId = Long.valueOf(studentData.get(0));
                log.info("updateUptodateData() | studentId : " + studentId);
                Student student = Student.findStudent(studentId);

                if(student != null && student.getStudentClass().equals(batch)){
                    log.info("updateUptodateData() | student id : " + student.getId()+" | dataState : "+student.getDataState());
                    if(student.getDataState().equals(StudentDataState.UPTODATE)){
                        student = setStudent(studentData,student);
                        student.persist();
                        result = true;
                    }else{
                        log.info("updateUptodateData() | student id : "+studentId+" is not up to date data");
                        result = false;
                    }
                } else{
                    log.info("updateUptodateData() | student id : "+studentId+" is not found");
                    result = false;
                }
            }catch (Exception e){
                log.info("updateUptodateData() | error : "+e.getMessage());
                e.printStackTrace();
            }
        }else{
            result = false;
        }
        return result;
    }

    private String generateUsername(Student student, Batch batch){
        if(student == null || batch == null){
            return null;
        }else{
            String username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+student.getFirstnameEn().toLowerCase();
            String uniqueUsername = generateUniqueUsername(batch, username.toLowerCase(), student, 1);
            log.info("generateUsername() | uniqueUsername : "+uniqueUsername);
            return uniqueUsername;
        }
    }

    private String generateUniqueUsername(Batch batch, String username, Student student, int i){ // i =start with 1
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

    private Student setStudent(ArrayList<String> studentData, Student student) throws ParseException {
        int col = 1;
        if(studentData != null){
            if(student == null){
                student = new Student();
            }
            student.setTitleTh(studentData.get(col++));
            col++;
            student.setFirstnameTh(studentData.get(col++));
            student.setLastnameTh(studentData.get(col++));
            student.setFullNameTh(studentData.get(col++));
            student.setTitleEn(studentData.get(col++));
            student.setFirstnameEn(studentData.get(col++));
            student.setLastnameEn(studentData.get(col++));
            student.setFullNameEn(studentData.get(col++));
            student.setNickname(studentData.get(col++));
            student.setSex(studentData.get(col++));

            String _birthDate = studentData.get(col++);
            if(_birthDate != null && !_birthDate.equals("")){
                try{
                    Date birthDate = df.parse(_birthDate);
                    String bDateStr = dMyFormat.format(birthDate);
                    student.setBirthdate(birthDate);
                    student.setBdateString(bDateStr);
                }catch (Exception e){

                }

            }


            student.setIdcardNo(studentData.get(col++));
            student.setPassportNo(studentData.get(col++));
            student.setRopNo(studentData.get(col++));

            student.setPositionTh(studentData.get(col++));
            student.setPositionEn(studentData.get(col++));
            student.setInstitutionTh(studentData.get(col++));
            student.setInstitutionEn(studentData.get(col++));
            student.setInstitutionNo(studentData.get(col++));
            student.setInstitutionMooNo(studentData.get(col++));
            student.setInstitutionBuilding(studentData.get(col++));
            student.setInstitutionFloor(studentData.get(col++));
            student.setInstitutionSoi(studentData.get(col++));
            student.setInstitutionStreet(studentData.get(col++));
            student.setInstitutionSubdistrict(studentData.get(col++));
            student.setInstitutionDistrict(studentData.get(col++));
            student.setInstitutionProvince(studentData.get(col++));
            student.setInstitutionPostalCode(studentData.get(col++));
            student.setWorkFullAddress(studentData.get(col++));
            student.setWorktel1(studentData.get(col++));
            student.setWorktel2(studentData.get(col++));
            student.setWorkfax(studentData.get(col++));

            student.setMobile1(studentData.get(col++));
            student.setMobile2(studentData.get(col++));
            student.setEmail(studentData.get(col++));
            student.setLineId(studentData.get(col++));
            student.setFacebook(studentData.get(col++));

            student.setHomeNo(studentData.get(col++));
            student.setMooNo(studentData.get(col++));
            student.setBuilding(studentData.get(col++));
            student.setVillage(studentData.get(col++));
            student.setFloor(studentData.get(col++));
            student.setSoi(studentData.get(col++));
            student.setStreet(studentData.get(col++));
            student.setSubdistrict(studentData.get(col++));
            student.setDistrict(studentData.get(col++));
            student.setProvince(studentData.get(col++));
            student.setPostalCode(studentData.get(col++));
            student.setHomeFullAddress(studentData.get(col++));
            student.setHometel1(studentData.get(col++));
            student.setHometel2(studentData.get(col++));
            student.setHomefax(studentData.get(col++));

            student.setSendingAddress(studentData.get(col++));
            student.setMarriedStatus(studentData.get(col++));
            student.setSpouseFirstName(studentData.get(col++));
            student.setSpouseLastName(studentData.get(col++));
            student.setSpouseCareer(studentData.get(col++));
            student.setSpouseInstitution(studentData.get(col++));

            _birthDate = studentData.get(col++);
            if(_birthDate != null && !_birthDate.equals("")){
                try{
                    Date birthDate = df.parse(_birthDate);
                    String bDateStr = dMyFormat.format(birthDate);
                    student.setSpouseBirthDay(birthDate);
                    student.setSpouseBdateString(bDateStr);
                }catch (Exception e){

                }

            }

            student.setSpouseRace(studentData.get(col++));
            student.setSpouseNationality(studentData.get(col++));
            student.setSpouseReligion(studentData.get(col++));
            student.setCollboratorFirstName(studentData.get(col++));
            student.setCollboratorLastName(studentData.get(col++));
            student.setCollboratorFullName(studentData.get(col++));
            student.setCollboratorTel(studentData.get(col++));
            student.setCollboratorMobile(studentData.get(col++));
            student.setCollboratorFax(studentData.get(col++));
            student.setCollboratorEmail(studentData.get(col++));
            student.setCollboratorLineId(studentData.get(col++));

            student.setDriverTel(studentData.get(col++));
            student.setCarNo(studentData.get(col++));
            student.setCarBrand(studentData.get(col++));
            student.setCarColor(studentData.get(col++));
            student.setPlayGolf(studentData.get(col++));
            student.setHandyCap(studentData.get(col++));

            student.setGeneralFood(convertStringToBoolean(studentData.get(col++)));
            student.setHalalFood(convertStringToBoolean(studentData.get(col++)));
            student.setJFood(convertStringToBoolean(studentData.get(col++)));
            student.setVegeterianFood(convertStringToBoolean(studentData.get(col++)));
            student.setAllergiesSeaFood(convertStringToBoolean(studentData.get(col++)));
            student.setBeefFood(convertStringToBoolean(studentData.get(col++)));

            student.setOtherFood(studentData.get(col++));
            student.setSmoking(studentData.get(col++));
            student.setJacketSize(studentData.get(col++));
            student.setPoloSize(studentData.get(col++));
            student.setWaist(studentData.get(col++));
            student.setTall(studentData.get(col++));
            student.setReceiptDetailName(studentData.get(col++));
            student.setReceiptDetailAddress(studentData.get(col++));
            student.setTaxId(studentData.get(col++));
            student.setSubmitProfile(convertStringToBoolean(studentData.get(col++)));
            student.setGroupName(studentData.get(col++));
        }
       return student;
    }

    //
    //  Copy from roo controller for solve the auto delete roo controller problem
    //

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new MediaUpload());
        return "mediauploads/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("mediaupload", MediaUpload.findMediaUpload(id));
        uiModel.addAttribute("itemId", id);
        return "mediauploads/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("mediauploads", MediaUpload.findMediaUploadEntries(firstResult, sizeNo));
            float nrOfPages = (float) MediaUpload.countMediaUploads() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("mediauploads", MediaUpload.findAllMediaUploads());
        }
        return "mediauploads/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid MediaUpload mediaUpload, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, mediaUpload);
            return "mediauploads/update";
        }
        uiModel.asMap().clear();
        mediaUpload.merge();
        return "redirect:/mediauploads/" + encodeUrlPathSegment(mediaUpload.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, MediaUpload.findMediaUpload(id));
        return "mediauploads/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        MediaUpload mediaUpload = MediaUpload.findMediaUpload(id);
        mediaUpload.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/mediauploads";
    }

    void populateEditForm(Model uiModel, MediaUpload mediaUpload) {
        uiModel.addAttribute("mediaUpload", mediaUpload);
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

    int getLastRecord(Long classId){
        EntityManager entityManager = UserRegis.entityManager();
        Query query = entityManager.createQuery("select max(x.id) from UserRegis x WHERE x.studentProfile.studentClass.id = :class_id");
        /*System.out.println(">>lastUsername : classid = "+classId);*/
        query.setParameter("class_id", classId);
        List resultList = query.getResultList();
        /*if(resultList!=null)
            System.out.println(">>lastUsername : resultList size = "+resultList.size());
        else System.out.println(">>lastUsername : resultList is null");
        System.out.println(">>lastUsername : "+resultList.size()+" "+resultList.get(0));*/
        if(resultList.get(0)!=null){
            Long lastId = (Long)resultList.get(0);
            UserRegis lastUserRegis = UserRegis.findUserRegis(lastId);
            String lastUsername = lastUserRegis.getUsername();
            lastUsername = lastUsername.substring(lastUsername.length()-3);
            /*System.out.println(">>lastUsername"+lastUsername+" "+Integer.parseInt(lastUsername));*/
            return Integer.parseInt(lastUsername)+1;
        }
        else return 1;
    }

    private String bCryptEncode(String password){
        if(password == null){
            return null;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @RequestMapping(value = "/downloadFile")
    private void downloadFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String fileName = httpServletRequest.getParameter("fileName");
        if(fileName != null){
            File file = new File(Constant.INIT_TEMPLATE_FILE,fileName);
            if(file.exists()){
                try {
                    Path path = file.toPath();
                    OutputStream out = httpServletResponse.getOutputStream();
                    httpServletResponse.setContentType("application/vnd.ms-excel");
                    httpServletResponse.setHeader("Content-Disposition", "Attachment;Filename=\""+fileName+"\"");
                    Files.copy(path, out);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                log.info("downloadFile() : file name : "+fileName+" is not found");
            }
        }
    }
}
