package com.cma.web;

import com.cma.Batch;
import com.cma.Course;
import com.cma.Student;
import org.joda.time.format.DateTimeFormat;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequestMapping("/std_classes")
@Controller
@RooWebScaffold(path = "std_classes", formBackingObject = Batch.class)
public class Std_classController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Batch batch,BindingResult bindingResult,@RequestParam("file1") CommonsMultipartFile file1,
                         @RequestParam("file2") CommonsMultipartFile file2,@RequestParam("file3") CommonsMultipartFile file3,
                         @RequestParam("file4") CommonsMultipartFile file4,@RequestParam("file5") CommonsMultipartFile file5,
                         @RequestParam("directoryFile") CommonsMultipartFile directoryFile,
                         @RequestParam("activityCalendarFile") CommonsMultipartFile activityCalendarFile, Model uiModel,
                         HttpServletRequest httpServletRequest) {
        System.out.println(">>>>>>>>>>STD_CLASS Create");
        if (bindingResult.hasErrors()) {
            System.out.println("create batch error");
            populateEditForm(uiModel, batch);
            return "std_classes/create";
        }
        String name = batch.getNameEn();
        try {
            if(!file1.isEmpty()){

                String originalFilename = file1.getOriginalFilename();
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file1.transferTo(dest);
                batch.setFilename1(name + "_" + originalFilename);
                batch.setUploadfile1(true);
            }
            else {
                batch.setUploadfile1(false);
            }

            if(!file2.isEmpty()){
                String originalFilename = file2.getOriginalFilename();
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file2.transferTo(dest);
                batch.setFilename2(name + "_" + originalFilename);
                batch.setUploadfile2(true);
            }
            else {
                batch.setUploadfile2(false);
            }

            if(!file3.isEmpty()){
                String originalFilename = file3.getOriginalFilename();
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file3.transferTo(dest);
                batch.setFilename3(name + "_" + originalFilename);
                batch.setUploadfile3(true);
            }
            else {
                batch.setUploadfile3(false);
            }

            if(!file4.isEmpty()){
                String originalFilename = file4.getOriginalFilename();
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file4.transferTo(dest);
                batch.setFilename4(name + "_" + originalFilename);
                batch.setUploadfile4(true);
            }
            else {
                batch.setUploadfile4(false);
            }

            if(!file5.isEmpty()){
                String originalFilename = file5.getOriginalFilename();
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file5.transferTo(dest);
                batch.setFilename5(name + "_" + originalFilename);
                batch.setUploadfile5(true);
            }
            else {
                batch.setUploadfile5(false);
            }

            if(!directoryFile.isEmpty()){
                String originalFilename = directoryFile.getOriginalFilename();
                File dest = new File(Constant.BATCH_DIRECTORY_PATH + name + "_" + originalFilename);
                directoryFile.transferTo(dest);
                batch.setDirectory(name + "_" + originalFilename);
            }

            if(!activityCalendarFile.isEmpty()){
                String originalFilename = activityCalendarFile.getOriginalFilename();
                File dest = new File(Constant.BATCH_ACTIVITYCALENDAR_PATH + name + "_" + originalFilename);
                activityCalendarFile.transferTo(dest);
                batch.setActivityCalendar(name + "_" + originalFilename);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        uiModel.asMap().clear();
        batch.persist();
        return "redirect:/std_classes/" + encodeUrlPathSegment(batch.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Batch());
        return "std_classes/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("batch", Batch.findBatch(id));
        uiModel.addAttribute("itemId", id);
        return "std_classes/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("batches", Batch.findBatchEntries(firstResult, sizeNo));
            float nrOfPages = (float) Batch.countBatches() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("batches", Batch.findAllBatches());
        }
        addDateTimeFormatPatterns(uiModel);
        return "std_classes/list";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST, produces = "text/html")
    public String update(@Valid Batch batch, BindingResult bindingResult,@RequestParam("file1") CommonsMultipartFile file1,
                         @RequestParam("file2") CommonsMultipartFile file2,@RequestParam("file3") CommonsMultipartFile file3,
                         @RequestParam("file4") CommonsMultipartFile file4,@RequestParam("file5") CommonsMultipartFile file5,
                         @RequestParam("directoryFile") CommonsMultipartFile directoryFile,
                         @RequestParam("activityCalendarFile") CommonsMultipartFile activityCalendarFile, Model uiModel,
                         HttpServletRequest httpServletRequest) {
        Long classId = Long.parseLong(httpServletRequest.getParameter("std_class_id"));
        Batch oldClass = Batch.findBatch(classId);

        System.out.println(">>>>>>>>>>STD_CLASS Update");
        //std_class.setType(httpServletRequest.getParameter("_type_id"));

        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, batch);
            return "std_classes/update";
        }

        String name = batch.getNameEn();
        try {
            if(!file1.isEmpty()){
                String originalFilename = file1.getOriginalFilename();
                //System.out.println(">>>>>>update attachmail"+Constant.BATCH_FILE_PATH+oldClass.getFilename1());
                if(oldClass.getFilename1()!=null&&!oldClass.getFilename1().equals("")){
                    //System.out.println(">>>>>>update attachmail"+Constant.BATCH_FILE_PATH+oldClass.getFilename1());
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getFilename1());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file1.transferTo(dest);
                batch.setFilename1(name + "_" + originalFilename);
                batch.setUploadfile1(true);
            }
            else {
                batch.setFilename1(oldClass.getFilename1());
                batch.setUploadfile1(oldClass.getUploadfile1());
            }

            if(!file2.isEmpty()){
                String originalFilename = file2.getOriginalFilename();
                if(oldClass.getFilename2()!=null&&!oldClass.getFilename2().equals("")){
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getFilename2());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file2.transferTo(dest);
                batch.setFilename2(name + "_" + originalFilename);
                batch.setUploadfile2(true);
            }
            else {
                batch.setFilename2(oldClass.getFilename2());
                batch.setUploadfile2(oldClass.getUploadfile2());
            }

            if(!file3.isEmpty()){
                String originalFilename = file3.getOriginalFilename();
                if(oldClass.getFilename3()!=null&&!oldClass.getFilename3().equals("")){
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getFilename3());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file3.transferTo(dest);
                batch.setFilename3(name + "_" + originalFilename);
                batch.setUploadfile3(true);
            }
            else {
                batch.setFilename3(oldClass.getFilename3());
                batch.setUploadfile3(oldClass.getUploadfile3());
            }

            if(!file4.isEmpty()){
                String originalFilename = file4.getOriginalFilename();
                if(oldClass.getFilename4()!=null&&!oldClass.getFilename4().equals("")){
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getFilename4());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file4.transferTo(dest);
                batch.setFilename4(name + "_" + originalFilename);
                batch.setUploadfile4(true);
            }
            else {
                batch.setFilename4(oldClass.getFilename4());
                batch.setUploadfile4(oldClass.getUploadfile4());
            }

            if(!file5.isEmpty()){
                String originalFilename = file5.getOriginalFilename();
                if(oldClass.getFilename5()!=null&&!oldClass.getFilename5().equals("")){
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getFilename5());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_FILE_PATH + name + "_" + originalFilename);
                file5.transferTo(dest);
                batch.setFilename5(name + "_" + originalFilename);
                batch.setUploadfile5(true);
            }
            else {
                batch.setFilename5(oldClass.getFilename5());
                batch.setUploadfile5(oldClass.getUploadfile5());
            }

            if(!directoryFile.isEmpty()){
                String originalFilename = directoryFile.getOriginalFilename();
                if(oldClass.getDirectory()!=null&&!oldClass.getDirectory().equals("")){
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getDirectory());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_DIRECTORY_PATH + name + "_" + originalFilename);
                directoryFile.transferTo(dest);
                batch.setDirectory(name + "_" + originalFilename);
            }else{
                batch.setDirectory(oldClass.getDirectory());
            }

            if(!activityCalendarFile.isEmpty()){
                String originalFilename = activityCalendarFile.getOriginalFilename();
                if(oldClass.getActivityCalendar()!=null&&!oldClass.getActivityCalendar().equals("")){
                    File oldFile = new File(Constant.BATCH_FILE_PATH+oldClass.getActivityCalendar());
                    oldFile.delete();
                }
                File dest = new File(Constant.BATCH_ACTIVITYCALENDAR_PATH + name + "_" + originalFilename);
                activityCalendarFile.transferTo(dest);
                batch.setActivityCalendar(name + "_" + originalFilename);
            }else{
                batch.setActivityCalendar(oldClass.getActivityCalendar());
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        uiModel.asMap().clear();
        batch.merge();
        return "redirect:/std_classes/" + encodeUrlPathSegment(batch.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Batch.findBatch(id));
        return "std_classes/update";
    }

    @RequestMapping(value = "/{id}", params="delete", produces = "text/html")
    public String delete2(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Batch batch = Batch.findBatch(id);
        batch.remove();                                           /**/
        uiModel.asMap().clear();
        return "redirect:/std_classes";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Batch batch = Batch.findBatch(id);
        batch.remove();
        uiModel.asMap().clear();
        return "redirect:/std_classes";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        Locale locale = new Locale("th", "TH");
        uiModel.addAttribute("batch_start_date_date_format", DateTimeFormat.patternForStyle("M-", locale));
        uiModel.addAttribute("batch_end_date_date_format", DateTimeFormat.patternForStyle("M-",locale ));
    }

    void populateEditForm(Model uiModel, Batch batch) {
        uiModel.addAttribute("batch", batch);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("student", Student.findAllStudents());

        List courseList = Course.findAllCourses();
        uiModel.addAttribute("courseList",courseList);

        List typeList = new ArrayList();
        typeList.add("Senior");
        typeList.add("Junior");
        uiModel.addAttribute("typeList",typeList);
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
