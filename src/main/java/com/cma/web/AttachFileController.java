package com.cma.web;

import com.cma.AttachFile;
import com.cma.Student;
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
import java.io.UnsupportedEncodingException;
import java.util.Date;

@RequestMapping("/attachfiles")
@Controller
@RooWebScaffold(path = "attachfiles", formBackingObject = AttachFile.class)
public class AttachFileController {
    /*private String filePath = "d:/tmp/";*/
    private String filePath = "/cma/http/uploadfile/";
    private long limitSize = 1024000L;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@RequestParam("photo") CommonsMultipartFile photo/*,@RequestParam("namecard") CommonsMultipartFile namecard*/,@RequestParam("idcard") CommonsMultipartFile idcard ,@RequestParam("passport") CommonsMultipartFile passport ,@RequestParam("apec") CommonsMultipartFile apec ,@RequestParam("pf") CommonsMultipartFile pf ,@RequestParam("slip1") CommonsMultipartFile slip1,@RequestParam("slip2") CommonsMultipartFile slip2,@RequestParam("holdingTax") CommonsMultipartFile holdingTax , Model uiModel, HttpServletRequest httpServletRequest) {

        String name = getCode();
        String filename = "";
        Long id = Long.parseLong(httpServletRequest.getParameter("std_profile_id"));
        uiModel.addAttribute("std_profile", Student.findStudent(id));
        AttachFile attachFile = Student.findStudent(id).getAttachFile();

        System.out.println(">>>>>>>>>>>>AttachFileController Start to upload file :id"+id);

        boolean isEmpty = true;
        boolean uploadPhoto = false;
        boolean uploadIdcard = false;
        boolean uploadPassport = false;
        boolean uploadApec = false;
        boolean uploadPf = false;
        boolean uploadSlip1 = false;
        boolean uploadSlip2 = false;
        boolean uploadHoldingTax = false;
        boolean stopUpload = false;

        if(httpServletRequest.getParameter("photo_button")!=null){
            uploadPhoto=true;
        }
        else if(httpServletRequest.getParameter("idcard_button")!=null){
            uploadIdcard=true;
        }
        else if(httpServletRequest.getParameter("passport_button")!=null){
            uploadPassport=true;
        }
        else if(httpServletRequest.getParameter("apec_button")!=null){
            uploadApec=true;
        }
        else if(httpServletRequest.getParameter("pf_button")!=null){
            uploadPf=true;
        }
        else if(httpServletRequest.getParameter("slip1_button")!=null){
            uploadSlip1=true;
        }
        else if(httpServletRequest.getParameter("slip2_button")!=null){
            uploadSlip2=true;
        }
        else if(httpServletRequest.getParameter("holdingTax_button")!=null){
            uploadHoldingTax=true;
        }

        try {

            if(!photo.isEmpty()&uploadPhoto){
                if(photo.getSize()>limitSize){
                    uiModel.addAttribute("messagePhoto","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = photo.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "photo" + name + fileType;
                    if(attachFile.getPhotoFileName()!=null&&!attachFile.getPhotoFileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getPhotoFileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>photo name : "+filename);
                    attachFile.setPhotoFileName(filename);
                    File destPhoto = new File(filePath + filename);
                    photo.transferTo(destPhoto);
                    attachFile.setUploadPhoto(true);
                    isEmpty=false;
                }
            }

            if(!idcard.isEmpty()&uploadIdcard){
                if(idcard.getSize()>limitSize){
                    uiModel.addAttribute("messageIdcard","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = idcard.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "idcard" + name + fileType;
                    if(attachFile.getIdcardFileName()!=null&&!attachFile.getIdcardFileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getIdcardFileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>idcard name : "+filename);
                    attachFile.setIdcardFileName(filename);
                    File destIdcard = new File(filePath + filename);
                    idcard.transferTo(destIdcard);
                    attachFile.setUploadIdcard(true);
                    isEmpty=false;
                }
            }

            if(!passport.isEmpty()&uploadPassport){
                if(passport.getSize()>limitSize){
                    uiModel.addAttribute("messagePassport","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = passport.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "passport" + name + fileType;
                    if(attachFile.getPassportFileName()!=null&&!attachFile.getPassportFileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getPassportFileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>passport name : "+filename);
                    attachFile.setPassportFileName(filename);
                    File destPassport = new File(filePath + filename );
                    passport.transferTo(destPassport);
                    attachFile.setUploadPassport(true);
                    isEmpty=false;
                }
            }

            if(!apec.isEmpty()&uploadApec){
                if(apec.getSize()>limitSize){
                    uiModel.addAttribute("messageApec","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = apec.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "apec" + name + fileType;
                    if(attachFile.getApecFileName()!=null&&!attachFile.getApecFileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getApecFileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>apec name : "+filename);
                    attachFile.setApecFileName(filename);
                    File destApec = new File(filePath + filename);
                    apec.transferTo(destApec);
                    attachFile.setUploadApec(true);
                    isEmpty=false;
                }
            }

            if(!pf.isEmpty()&uploadPf){
                if(pf.getSize()>limitSize){
                    uiModel.addAttribute("messagePf","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = pf.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "pf" + name + fileType;
                    if(attachFile.getPfFileName()!=null&&!attachFile.getPfFileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getPfFileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>pf name : "+filename);
                    attachFile.setPfFileName(filename);
                    File destpf = new File(filePath + filename);
                    pf.transferTo(destpf);
                    attachFile.setUploadPf(true);
                    isEmpty=false;
                }
            }

            if(!slip1.isEmpty()&uploadSlip1){
                if(slip1.getSize()>limitSize){
                    uiModel.addAttribute("messageSlip1","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = slip1.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "slip1" + name + fileType;
                    if(attachFile.getSlip1FileName()!=null&&!attachFile.getSlip1FileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getSlip1FileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>slip1 name : "+filename);
                    attachFile.setSlip1FileName(filename);
                    File destslip1 = new File(filePath + filename);
                    slip1.transferTo(destslip1);
                    attachFile.setUploadSlip1(true);
                    isEmpty=false;
                }
            }

            if(!slip2.isEmpty()&uploadSlip2){
                if(slip2.getSize()>limitSize){
                    uiModel.addAttribute("messageSlip2","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }
                else {
                    String originalFilename = slip2.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "slip2" + name + fileType;
                    if(attachFile.getSlip2FileName()!=null&&!attachFile.getSlip2FileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getSlip2FileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>slip2 name : "+filename);
                    attachFile.setSlip2FileName(filename);
                    File destslip2 = new File(filePath + filename);
                    slip2.transferTo(destslip2);
                    attachFile.setUploadSlip2(true);
                    isEmpty=false;
                }
            }

            if(!holdingTax.isEmpty() & uploadHoldingTax){
                if(holdingTax.getSize() > limitSize){
                    uiModel.addAttribute("messageHoldingTax","ไฟล์ขนาดใหญ่เกินกว่าที่กำหนด");
                    stopUpload = true;
                }else{
                    String originalFilename = holdingTax.getOriginalFilename();
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                    filename = "holdingTax" + name + fileType;
                    if(attachFile.getHoldingTaxFileName()!=null&&!attachFile.getHoldingTaxFileName().equals("")){
                        File oldFile = new File(filePath+attachFile.getHoldingTaxFileName());
                        oldFile.delete();
                    }
                    //System.out.println(">>>>>>>>>>>>holdingtax name : "+filename);
                    attachFile.setHoldingTaxFileName(filename);
                    File destholdingtax = new File(filePath + filename);
                    holdingTax.transferTo(destholdingtax);
                    attachFile.setUploadHoldingTax(true);
                    isEmpty=false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "attachfiles/create";
        }

        populateEditForm(uiModel, attachFile);
        if(!isEmpty && !stopUpload){
            //System.out.println(">>>>Can upload files");
            uiModel.addAttribute("message","Upload ไฟล์สำเร็จ");
        }
        else if(stopUpload){
            //System.out.println(">>>>Can not upload files");
            uiModel.addAttribute("warning_message","ไฟล์มีขนาดใหญ่เกินกว่ากำหนด  (แต่ละไฟล์ขนาดไม่เกิน "+limitSize/1000000+" MB)");
        }
        else {
            uiModel.addAttribute("warning_message","กรุณากดเลือกไฟล์ที่จะแนบก่อน");
        }
        attachFile.merge();
        //System.out.println(">>>>>>>>>>>>AttachFileController upload file complete");
        String admin = httpServletRequest.getParameter("admin");
        if(admin!=null&&admin.equals("yes")){
            uiModel.addAttribute("editByAdmin","yes");
        }
        return "attachfiles/create";
    }

    @RequestMapping(value = "/{id}",params = "createForm", produces = "text/html")
    public String createForm(@PathVariable("id") Long id,Model uiModel,HttpServletRequest request) {
        AttachFile attachFile = Student.findStudent(id).getAttachFile();
        populateEditForm(uiModel, attachFile);
        Student std_profile = Student.findStudent(id);
        uiModel.addAttribute("std_profile",std_profile);
        String admin = request.getParameter("admin");
        if(admin!=null&&admin.equals("yes")){
            uiModel.addAttribute("editByAdmin","yes");
        }
        return "attachfiles/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("attachfile", AttachFile.findAttachFile(id));
        uiModel.addAttribute("itemId", id);
        return "attachfiles/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("attachfiles", AttachFile.findAttachFileEntries(firstResult, sizeNo));
            float nrOfPages = (float) AttachFile.countAttachFiles() / sizeNo;

            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("attachfiles", AttachFile.findAllAttachFiles());
        }
        return "attachfiles/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AttachFile attachFile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, attachFile);
            return "attachfiles/update";
        }
        uiModel.asMap().clear();
        attachFile.merge();
        return "redirect:/attachfiles/" + encodeUrlPathSegment(attachFile.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, AttachFile.findAttachFile(id));
        return "attachfiles/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AttachFile attachFile = AttachFile.findAttachFile(id);
        attachFile.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/attachfiles";
    }

    void populateEditForm(Model uiModel, AttachFile attachFile) {
        uiModel.addAttribute("attachFile", attachFile);
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

    synchronized String getCode(){
        return (new Date().getTime())+"";
    }
}
