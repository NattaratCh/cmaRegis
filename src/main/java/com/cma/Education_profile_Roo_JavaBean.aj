// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Education_profile;
import com.cma.Student;

privileged aspect Education_profile_Roo_JavaBean {
    
    public String Education_profile.getDegree() {
        return this.degree;
    }
    
    public void Education_profile.setDegree(String degree) {
        this.degree = degree;
    }
    
    public String Education_profile.getField() {
        return this.field;
    }
    
    public void Education_profile.setField(String field) {
        this.field = field;
    }
    
    public String Education_profile.getUniversity() {
        return this.university;
    }
    
    public void Education_profile.setUniversity(String university) {
        this.university = university;
    }
    
    public String Education_profile.getGraduateYear() {
        return this.graduateYear;
    }
    
    public void Education_profile.setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }
    
    public Student Education_profile.getStudentProfile() {
        return this.studentProfile;
    }
    
    public void Education_profile.setStudentProfile(Student studentProfile) {
        this.studentProfile = studentProfile;
    }
    
}
