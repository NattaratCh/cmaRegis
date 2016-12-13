package com.cma.web;

import com.cma.Student;
import com.cma.Training_profile;
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

@RequestMapping("/training_profiles")
@Controller
@RooWebScaffold(path = "training_profiles", formBackingObject = Training_profile.class)
public class Training_profileController {
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Training_profile training_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, training_profile);
            return "training_profiles/create";
        }
        uiModel.asMap().clear();
        training_profile.persist();
        return "redirect:/training_profiles/" + encodeUrlPathSegment(training_profile.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Training_profile());
        return "training_profiles/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("training_profile", Training_profile.findTraining_profile(id));
        uiModel.addAttribute("itemId", id);
        return "training_profiles/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("training_profiles", Training_profile.findTraining_profileEntries(firstResult, sizeNo));
            float nrOfPages = (float) Training_profile.countTraining_profiles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("training_profiles", Training_profile.findAllTraining_profiles());
        }
        return "training_profiles/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Training_profile training_profile, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        Training_profile old_training_profile = Training_profile.findTraining_profile(training_profile.getId());
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, training_profile);
            return "training_profiles/update";
        }
        uiModel.asMap().clear();
        training_profile.setStudentProfile(old_training_profile.getStudentProfile());
        training_profile.merge();
        return "redirect:/std_profiles/"+old_training_profile.getStudentProfile().getId()+"?form3";
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        Training_profile training_profile = Training_profile.findTraining_profile(id);
        populateEditForm(uiModel, training_profile);
        uiModel.addAttribute("std_profile",training_profile.getStudentProfile());
        return "training_profiles/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Training_profile training_profile = Training_profile.findTraining_profile(id);
        training_profile.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/training_profiles";
    }

    void populateEditForm(Model uiModel, Training_profile training_profile) {
        uiModel.addAttribute("training_profile", training_profile);
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
