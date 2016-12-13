package com.cma.web;

import com.cma.Batch;
import com.cma.Student;
import com.cma.UserRegis;
import com.cma.UserRegisRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.cma.common.authenManager.sha256;
import static com.cma.common.authenManager.validateStrongPassword;

@RequestMapping("/userlogins")
@Controller
@RooWebScaffold(path = "userlogins", formBackingObject = UserRegis.class)
public class UserloginController {

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    private static Log log = LogFactory.getLog(UserloginController.class);

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    private void reLogin(UserRegis userRegis){
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication currentUser = sc.getAuthentication();
        List roles = new ArrayList();
        roles.add(userRegis.getUserRole().getRole_name());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser.getPrincipal(),currentUser.getCredentials(),getGrantedAuthorities(roles));
        sc.setAuthentication(authentication);
        SecurityContextHolder.setContext(sc);
    }

    private boolean checkLastChangePwd(UserRegis userRegis){
        long currentTime = new Date().getTime();

        long lastChangeTime = userRegis.getLastChangePwd().getTime();
        long diff = (currentTime-lastChangeTime)/(24*60*60*1000);

        return diff > 90;
    }

    @RequestMapping(value = "/checkRole", produces = "text/html")
    public String checkRole(Model uiModel,HttpServletRequest request) {

        UserRegis userRegis = findUserRegisObject();
        String user_role = (userRegis ==null)?null : userRegis.getUserRole().getRole_name();

        log.info("checkRole() | login By "+ userRegis.getUsername()+" user_id "+ userRegis.getId()+" role "+user_role);

        //ROLE_ADMIN
        if(user_role.equals("ROLE_ADMIN")) {
            if(checkLastChangePwd(userRegis)){
                uiModel.addAttribute("warning_message","คุณไม่ได้เปลี่ยนรหัสผ่านเกิน90วันแล้ว กรุณเปลี่ยนรหัสผ่าน");
                UserRegisRole role_user = null;
                List roleList = UserRegisRole.findAllUserRegisRoles();
                for(int i=0;i < roleList.size();i++){
                    role_user = (UserRegisRole)roleList.get(i);
                    if(role_user.getRole_name().equals("ROLE_ADMIN_EXPIRED")){
                        break;
                    }
                }
                userRegis.setUserRole(role_user);
                userRegis.merge();
                reLogin(userRegis);
                uiModel.addAttribute("user_id", userRegis.getId());
                return "userlogins/changePwd";
            }
            else {
                return "userlogins/index";
            }
        }
        else if(user_role.equals("ROLE_USER_UNCHANGE")){
                uiModel.addAttribute("student", userRegis.getStudentProfile());

                //set new role
                List roleList = UserRegisRole.findAllUserRegisRoles();
                UserRegisRole role_user = null;
                for(int i=0;i < roleList.size();i++){
                    role_user = (UserRegisRole)roleList.get(i);
                    if(role_user.getRole_name().equals("ROLE_USER")){
                        break;
                    }
                }
                userRegis.setUserRole(role_user);
                userRegis.merge();
                uiModel.addAttribute("std_class", userRegis.getStudentProfile().getStudentClass());
                return "userlogins/index";
        }
        else if(user_role.equals("ROLE_USER")){
                uiModel.addAttribute("std_class", userRegis.getStudentProfile().getStudentClass());
                uiModel.addAttribute("student", userRegis.getStudentProfile());
                return "userlogins/index";
        }
        //ROLE_ADMIN_EXPIRED
        else {
            uiModel.addAttribute("warning_message","คุณไม่ได้เปลี่ยนรหัสผ่านเกิน90วันแล้ว กรุณเปลี่ยนรหัสผ่าน");
            uiModel.addAttribute("user_id", userRegis.getId());
            return "userlogins/changePwd";
        }
    }

    @RequestMapping( value = "changePwd" , produces = "text/html")
    public String changePwd(Model uiModel){
        UserRegis userRegis = findUserRegisObject();
        uiModel.addAttribute("user_id", userRegis.getId());
        uiModel.addAttribute("student", findUserRegisObject().getStudentProfile());
        return "userlogins/changePwd";
    }

    @RequestMapping(value = "/{id}", params = "submitChangePwd" , produces = "text/html")
    public String submitChangePwd(@PathVariable("id") Long id ,Model uiModel,HttpServletRequest request){
        String newPassword = request.getParameter("newPassword");
        UserRegis userRegis = UserRegis.findUserRegis(id);
        uiModel.addAttribute("student", userRegis.getStudentProfile());
        uiModel.addAttribute("user_id", userRegis.getId());
        if(newPassword.equals("")){
            uiModel.addAttribute("error_message","กรุณากรอกรหัสผ่าน");
            return "userlogins/changePwd";
        }
        else if(newPassword.length()<8){
            uiModel.addAttribute("error_message","รหัสผ่านต้องมีความยาวอย่างน้อย 8 หลัก");
            return "userlogins/changePwd";
        }
        else if(!validateStrongPassword(newPassword)){
            uiModel.addAttribute("error_message","รหัสต้องประกอบด้วยอักขระอย่างน้อย 3 รูปแบบ ตัวอักษรภาษอังกฤษตัวเล็ก ตัวภาษาอังกฤษตัวใหญ่ ตัวเลขหรืออักขระพิเศษ(@,$,!,#,&)อยู่ในรหัสผ่าน");
            return "userlogins/changePwd";
        }
        else if(!checkFiveBeforePassword( newPassword , userRegis)){
            uiModel.addAttribute("error_message","รหัสผ่านต้องไม่ใช่รหัสเดิมอย่างน้อย5ครั้งย้อนหลัง กรุณากรอกใหม่");
            return "userlogins/changePwd";
        }

        int pointer = userRegis.getPointer();
        if(pointer==1){
            userRegis.setPassword2(sha256(newPassword));
            userRegis.setPointer(2);
        }
        else if(pointer==2){
            userRegis.setPassword3(sha256(newPassword));
            userRegis.setPointer(3);
        }
        else if(pointer==3){
            userRegis.setPassword4(sha256(newPassword));
            userRegis.setPointer(4);
        }
        else if(pointer==4){
            userRegis.setPassword5(sha256(newPassword));
            userRegis.setPointer(5);
        }
        else if(pointer==5){
            userRegis.setPassword1(sha256(newPassword));
            userRegis.setPointer(1);
        }

        userRegis.setPassword(sha256(newPassword));
        userRegis.setLastChangePwd(new Date());
        if(userRegis.getUserRole().getRole_name().equals("ROLE_ADMIN_EXPIRED")){
            //set new role
            List roleList = UserRegisRole.findAllUserRegisRoles();
            UserRegisRole role_user = null;
            for(int i=0;i < roleList.size();i++){
                role_user = (UserRegisRole)roleList.get(i);
                if(role_user.getRole_name().equals("ROLE_ADMIN")){
                    break;
                }
            }
            userRegis.setUserRole(role_user);
            userRegis.merge();
            reLogin(userRegis);
        }
        else userRegis.merge();

        UserRegis currentUserRegis = findUserRegisObject();
        if(!currentUserRegis.getUserRole().getRole_name().equals("ROLE_ADMIN"))
            uiModel.addAttribute("student", currentUserRegis.getStudentProfile());
        uiModel.addAttribute("message","รหัสผ่านของคุณถูกเปลี่ยนเรียบร้อยแล้ว.");
        return "userlogins/changePwd";
    }

    public static UserRegis findUserRegisObject(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List userList = UserRegis.findAllUserRegises();
        UserRegis userRegis = null;
        for(int i = 0;i < userList.size();i++){
            userRegis = (UserRegis)userList.get(i);
            if(userRegis.getUsername().equals(username)){
                return userRegis;
            }
        }
        return null;
    }

    //
    // Copy from Roo controller
    //
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UserRegis userRegis, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRegis);
            return "userlogins/create";
        }

        String password = userRegis.getPassword();

        if(password.length()<8){
            uiModel.addAttribute("error_message","รหัสผ่านต้องมีความยาวอย่างน้อย 8 หลัก");
            populateEditForm(uiModel, userRegis);
            return "userlogins/create";
        }
        else if(!validateStrongPassword(password)){
            uiModel.addAttribute("error_message","รหัสต้องประกอบด้วยอักขระอย่างน้อย 3 รูปแบบ ตัวอักษรภาษอังกฤษตัวเล็ก ตัวภาษาอังกฤษตัวใหญ่ ตัวเลขหรืออักขระพิเศษ(@,$,!,#,&)อยู่ในรหัสผ่าน");
            populateEditForm(uiModel, userRegis);
            return "userlogins/create";
        }

        uiModel.asMap().clear();
        userRegis.setPassword(sha256(password));
        userRegis.setPassword1(sha256(password));
        userRegis.setPointer(1);
        userRegis.setLastChangePwd(new Date());
        userRegis.persist();
        return "redirect:/userlogins/" + encodeUrlPathSegment(userRegis.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UserRegis());
        return "userlogins/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("userregis", UserRegis.findUserRegis(id));
        uiModel.addAttribute("itemId", id);
        return "userlogins/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("userregises", UserRegis.findUserRegisEntries(firstResult, sizeNo));
            float nrOfPages = (float) UserRegis.countUserRegises() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("userregises", UserRegis.findAllUserRegises());
        }
        return "userlogins/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserRegis userRegis, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        UserRegis userRegis_old = UserRegis.findUserRegis(Long.parseLong(httpServletRequest.getParameter("id")));
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userRegis);
            return "userlogins/update";
        }
        uiModel.asMap().clear();
        userRegis.setPassword(userRegis_old.getPassword());
        userRegis.setPassword1(userRegis_old.getPassword1());
        userRegis.setPassword2(userRegis_old.getPassword2());
        userRegis.setPassword3(userRegis_old.getPassword3());
        userRegis.setPassword4(userRegis_old.getPassword4());
        userRegis.setPassword5(userRegis_old.getPassword5());
        userRegis.setLoginTimes(userRegis_old.getLoginTimes());
        userRegis.setPointer(userRegis_old.getPointer());
        userRegis.setStudentProfile(userRegis_old.getStudentProfile());
        userRegis.setLastChangePwd(userRegis_old.getLastChangePwd());
        userRegis.merge();
        return "redirect:/userlogins/" + encodeUrlPathSegment(userRegis.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, UserRegis.findUserRegis(id));
        uiModel.addAttribute("user_role_id" , UserRegis.findUserRegis(id).getUserRole().getId());
        return "userlogins/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        UserRegis userRegis = UserRegis.findUserRegis(id);
        /*if(userlogin==null)System.out.println("userlogin is null "+id);
        else System.out.println("userlogin is not null "+id);*/
        userRegis.remove();
        uiModel.asMap().clear();
        return "redirect:/userlogins";
    }

    void populateEditForm(Model uiModel, UserRegis userRegis) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("userRegis", userRegis);
        uiModel.addAttribute("batches", Batch.findAllBatches());
        uiModel.addAttribute("students", Student.findAllStudents());
        uiModel.addAttribute("userroles", UserRegisRole.findAllUserRegisRoles());
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

    Boolean checkFiveBeforePassword(String newPassword,UserRegis userRegis){
        String encryptPassword = sha256(newPassword);
        if(userRegis.getPassword1()!=null){
            /*System.out.println(">>>> check password1");
            System.out.println(">>>> "+userlogin.getPassword1());
            System.out.println(">>>> "+encryptPassword);*/
            if(userRegis.getPassword1().equals(encryptPassword))return false;
        }
        if(userRegis.getPassword2()!=null){
            /*System.out.println(">>>> check password2");
            System.out.println(">>>> "+userlogin.getPassword2());
            System.out.println(">>>> "+encryptPassword);*/
            if(userRegis.getPassword2().equals(encryptPassword))return false;
        }
        if(userRegis.getPassword3()!=null){
            /*System.out.println(">>>> check password3");
            System.out.println(">>>> "+userlogin.getPassword3());
            System.out.println(">>>> "+encryptPassword);*/
            if(userRegis.getPassword3().equals(encryptPassword))return false;
        }
        if(userRegis.getPassword4()!=null){
            /*System.out.println(">>>> check password4");
            System.out.println(">>>> "+userlogin.getPassword4());
            System.out.println(">>>> "+encryptPassword);*/
            if(userRegis.getPassword4().equals(encryptPassword))return false;
        }
        if(userRegis.getPassword5()!=null){
            /*System.out.println(">>>> check password5");
            System.out.println(">>>> "+userlogin.getPassword5());
            System.out.println(">>>> "+encryptPassword);*/
            if(userRegis.getPassword5().equals(encryptPassword))return false;
        }
        return true;
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        Locale locale = new Locale("th", "TH");
        uiModel.addAttribute("userRegis_lastchangepwd_date_format", DateTimeFormat.patternForStyle("M-", locale));
    }

}
