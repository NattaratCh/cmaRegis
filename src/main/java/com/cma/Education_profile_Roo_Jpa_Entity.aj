// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Education_profile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Education_profile_Roo_Jpa_Entity {
    
    declare @type: Education_profile: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Education_profile.id;
    
    @Version
    @Column(name = "version")
    private Integer Education_profile.version;
    
    public Long Education_profile.getId() {
        return this.id;
    }
    
    public void Education_profile.setId(Long id) {
        this.id = id;
    }
    
    public Integer Education_profile.getVersion() {
        return this.version;
    }
    
    public void Education_profile.setVersion(Integer version) {
        this.version = version;
    }
    
}