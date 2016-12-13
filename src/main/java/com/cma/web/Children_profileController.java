package com.cma.web;

import com.cma.Children_profile;
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
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RequestMapping("/children_profiles")
@Controller
@RooWebScaffold(path = "children_profiles", formBackingObject = Children_profile.class)
public class Children_profileController {
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Children_profile children_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, children_profile);
            return "children_profiles/create";
        }
        uiModel.asMap().clear();
        children_profile.persist();
        return "redirect:/children_profiles/" + encodeUrlPathSegment(children_profile.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Children_profile());
        return "children_profiles/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("children_profile", Children_profile.findChildren_profile(id));
        uiModel.addAttribute("itemId", id);
        return "children_profiles/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("children_profiles", Children_profile.findChildren_profileEntries(firstResult, sizeNo));
            float nrOfPages = (float) Children_profile.countChildren_profiles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("children_profiles", Children_profile.findAllChildren_profiles());
        }
        addDateTimeFormatPatterns(uiModel);
        return "children_profiles/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Children_profile children_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        Children_profile old_children_profile = Children_profile.findChildren_profile(children_profile.getId());
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, children_profile);
            return "children_profiles/update";
        }
        uiModel.asMap().clear();
        //System.out.print(">>>update children "+children_profile.getYear()+" "+ children_profile.getMonth()+" "+ children_profile.getDay());
        Date childrenbday=null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yy");
            childrenbday = df.parse(children_profile.getChildrenBdateString());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        children_profile.setBirthday(childrenbday);
        children_profile.setStudentProfile(old_children_profile.getStudentProfile());
        children_profile.merge();
        return "redirect:/std_profiles/"+old_children_profile.getStudentProfile().getId()+"?form2";
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Children_profile children_profile = Children_profile.findChildren_profile(id);
        populateEditForm(uiModel, children_profile);
        uiModel.addAttribute("std_profile",children_profile.getStudentProfile());
        addSelectedItem(uiModel);
        return "children_profiles/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Children_profile children_profile = Children_profile.findChildren_profile(id);
        children_profile.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/children_profiles";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        Locale locale = new Locale("th", "TH");
        uiModel.addAttribute("children_profile_birthday_date_format", DateTimeFormat.patternForStyle("M-", locale));
    }

    void populateEditForm(Model uiModel, Children_profile children_profile) {
        uiModel.addAttribute("children_profile", children_profile);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("std_profiles", Student.findAllStudents());
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

    private void addSelectedItem(Model uiModel){
        //////////////////////////////////////////Year

        int maxyear = new Date().getYear()+1900-20;
        int minyear = maxyear-80;
        uiModel.addAttribute("maxyear",maxyear);
        uiModel.addAttribute("minyear",minyear);
    }
}
