// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Course;
import com.cma.CourseDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect CourseDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CourseDataOnDemand: @Component;
    
    private Random CourseDataOnDemand.rnd = new SecureRandom();
    
    private List<Course> CourseDataOnDemand.data;
    
    public Course CourseDataOnDemand.getNewTransientCourse(int index) {
        Course obj = new Course();
        setCode(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void CourseDataOnDemand.setCode(Course obj, int index) {
        String code = "code_" + index;
        if (code.length() > 20) {
            code = code.substring(0, 20);
        }
        obj.setCode(code);
    }
    
    public void CourseDataOnDemand.setName(Course obj, int index) {
        String name = "name_" + index;
        if (name.length() > 50) {
            name = name.substring(0, 50);
        }
        obj.setName(name);
    }
    
    public Course CourseDataOnDemand.getSpecificCourse(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Course obj = data.get(index);
        Long id = obj.getId();
        return Course.findCourse(id);
    }
    
    public Course CourseDataOnDemand.getRandomCourse() {
        init();
        Course obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Course.findCourse(id);
    }
    
    public boolean CourseDataOnDemand.modifyCourse(Course obj) {
        return false;
    }
    
    public void CourseDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Course.findCourseEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Course' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course obj = getNewTransientCourse(i);
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
