// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.AttachFile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect AttachFile_Roo_Jpa_Entity {
    
    declare @type: AttachFile: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AttachFile.id;
    
    @Version
    @Column(name = "version")
    private Integer AttachFile.version;
    
    public Long AttachFile.getId() {
        return this.id;
    }
    
    public void AttachFile.setId(Long id) {
        this.id = id;
    }
    
    public Integer AttachFile.getVersion() {
        return this.version;
    }
    
    public void AttachFile.setVersion(Integer version) {
        this.version = version;
    }
    
}
