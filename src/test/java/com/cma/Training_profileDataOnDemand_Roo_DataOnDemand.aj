// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.StudentDataOnDemand;
import com.cma.Training_profile;
import com.cma.Training_profileDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect Training_profileDataOnDemand_Roo_DataOnDemand {
    
    declare @type: Training_profileDataOnDemand: @Component;
    
    private Random Training_profileDataOnDemand.rnd = new SecureRandom();
    
    private List<Training_profile> Training_profileDataOnDemand.data;
    
    @Autowired
    StudentDataOnDemand Training_profileDataOnDemand.studentDataOnDemand;
    
    public Training_profile Training_profileDataOnDemand.getNewTransientTraining_profile(int index) {
        Training_profile obj = new Training_profile();
        setTrainingClass(obj, index);
        setTrainingInstitute(obj, index);
        setTrainingName(obj, index);
        setTrainingYear(obj, index);
        return obj;
    }
    
    public void Training_profileDataOnDemand.setTrainingClass(Training_profile obj, int index) {
        String trainingClass = "trainingClass_" + index;
        obj.setTrainingClass(trainingClass);
    }
    
    public void Training_profileDataOnDemand.setTrainingInstitute(Training_profile obj, int index) {
        String trainingInstitute = "trainingInstitute_" + index;
        obj.setTrainingInstitute(trainingInstitute);
    }
    
    public void Training_profileDataOnDemand.setTrainingName(Training_profile obj, int index) {
        String trainingName = "trainingName_" + index;
        obj.setTrainingName(trainingName);
    }
    
    public void Training_profileDataOnDemand.setTrainingYear(Training_profile obj, int index) {
        String trainingYear = "trainingYear_" + index;
        obj.setTrainingYear(trainingYear);
    }
    
    public Training_profile Training_profileDataOnDemand.getSpecificTraining_profile(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Training_profile obj = data.get(index);
        Long id = obj.getId();
        return Training_profile.findTraining_profile(id);
    }
    
    public Training_profile Training_profileDataOnDemand.getRandomTraining_profile() {
        init();
        Training_profile obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Training_profile.findTraining_profile(id);
    }
    
    public boolean Training_profileDataOnDemand.modifyTraining_profile(Training_profile obj) {
        return false;
    }
    
    public void Training_profileDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Training_profile.findTraining_profileEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Training_profile' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Training_profile>();
        for (int i = 0; i < 10; i++) {
            Training_profile obj = getNewTransientTraining_profile(i);
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
