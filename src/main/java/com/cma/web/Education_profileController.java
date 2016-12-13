package com.cma.web;

import com.cma.Education_profile;
import com.cma.Student;
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

@RequestMapping("/education_profiles")
@Controller
@RooWebScaffold(path = "education_profiles", formBackingObject = Education_profile.class)
public class Education_profileController {
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Education_profile education_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, education_profile);
            return "education_profiles/create";
        }
        uiModel.asMap().clear();
        education_profile.persist();
        return "redirect:/education_profiles/" + encodeUrlPathSegment(education_profile.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Education_profile());
        return "education_profiles/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("education_profile", Education_profile.findEducation_profile(id));
        uiModel.addAttribute("itemId", id);
        return "education_profiles/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("education_profiles", Education_profile.findEducation_profileEntries(firstResult, sizeNo));
            float nrOfPages = (float) Education_profile.countEducation_profiles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("education_profiles", Education_profile.findAllEducation_profiles());
        }
        return "education_profiles/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Education_profile education_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        Education_profile old_education_profile = Education_profile.findEducation_profile(education_profile.getId());
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, education_profile);
            return "education_profiles/update";
        }
        uiModel.asMap().clear();
        education_profile.setDegree(old_education_profile.getDegree());
        education_profile.setStudentProfile(old_education_profile.getStudentProfile());
        education_profile.merge();
        return "redirect:/std_profiles/"+old_education_profile.getStudentProfile().getId()+"?form3";
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Education_profile education_profile = Education_profile.findEducation_profile(id);
        populateEditForm(uiModel, education_profile);
        uiModel.addAttribute("std_profile",education_profile.getStudentProfile());
        return "education_profiles/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Education_profile education_profile = Education_profile.findEducation_profile(id);
        education_profile.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/education_profiles";
    }

    void populateEditForm(Model uiModel, Education_profile education_profile) {
        uiModel.addAttribute("education_profile", education_profile);
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
}
