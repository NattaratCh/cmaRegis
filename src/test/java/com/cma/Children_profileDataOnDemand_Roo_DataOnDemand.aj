// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Children_profile;
import com.cma.Children_profileDataOnDemand;
import com.cma.StudentDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect Children_profileDataOnDemand_Roo_DataOnDemand {
    
    declare @type: Children_profileDataOnDemand: @Component;
    
    private Random Children_profileDataOnDemand.rnd = new SecureRandom();
    
    private List<Children_profile> Children_profileDataOnDemand.data;
    
    @Autowired
    StudentDataOnDemand Children_profileDataOnDemand.studentDataOnDemand;
    
    public Children_profile Children_profileDataOnDemand.getNewTransientChildren_profile(int index) {
        Children_profile obj = new Children_profile();
        setBirthday(obj, index);
        setCareer(obj, index);
        setChildrenBdateString(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setNationality(obj, index);
        setRace(obj, index);
        setReligion(obj, index);
        return obj;
    }
    
    public void Children_profileDataOnDemand.setBirthday(Children_profile obj, int index) {
        Date birthday = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setBirthday(birthday);
    }
    
    public void Children_profileDataOnDemand.setCareer(Children_profile obj, int index) {
        String career = "career_" + index;
        obj.setCareer(career);
    }
    
    public void Children_profileDataOnDemand.setChildrenBdateString(Children_profile obj, int index) {
        String childrenBdateString = "childrenBdateString_" + index;
        obj.setChildrenBdateString(childrenBdateString);
    }
    
    public void Children_profileDataOnDemand.setFirstName(Children_profile obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }
    
    public void Children_profileDataOnDemand.setLastName(Children_profile obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    public void Children_profileDataOnDemand.setNationality(Children_profile obj, int index) {
        String nationality = "nationality_" + index;
        obj.setNationality(nationality);
    }
    
    public void Children_profileDataOnDemand.setRace(Children_profile obj, int index) {
        String race = "race_" + index;
        obj.setRace(race);
    }
    
    public void Children_profileDataOnDemand.setReligion(Children_profile obj, int index) {
        String religion = "religion_" + index;
        obj.setReligion(religion);
    }
    
    public Children_profile Children_profileDataOnDemand.getSpecificChildren_profile(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Children_profile obj = data.get(index);
        Long id = obj.getId();
        return Children_profile.findChildren_profile(id);
    }
    
    public Children_profile Children_profileDataOnDemand.getRandomChildren_profile() {
        init();
        Children_profile obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Children_profile.findChildren_profile(id);
    }
    
    public boolean Children_profileDataOnDemand.modifyChildren_profile(Children_profile obj) {
        return false;
    }
    
    public void Children_profileDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Children_profile.findChildren_profileEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Children_profile' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Children_profile>();
        for (int i = 0; i < 10; i++) {
            Children_profile obj = getNewTransientChildren_profile(i);
            try {
                obj.persist();
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
