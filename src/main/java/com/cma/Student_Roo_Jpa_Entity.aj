// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Student;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Student_Roo_Jpa_Entity {
    
    declare @type: Student: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Student.id;
    
    @Version
    @Column(name = "version")
    private Integer Student.version;
    
    public Long Student.getId() {
        return this.id;
    }
    
    public void Student.setId(Long id) {
        this.id = id;
    }
    
    public Integer Student.getVersion() {
        return this.version;
    }
    
    public void Student.setVersion(Integer version) {
        this.version = version;
    }
    
}
