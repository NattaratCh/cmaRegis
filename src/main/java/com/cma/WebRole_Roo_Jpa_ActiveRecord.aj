// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.WebRole;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect WebRole_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager WebRole.entityManager;
    
    public static final List<String> WebRole.fieldNames4OrderClauseFilter = java.util.Arrays.asList("authority");
    
    public static final EntityManager WebRole.entityManager() {
        EntityManager em = new WebRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long WebRole.countWebRoles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WebRole o", Long.class).getSingleResult();
    }
    
    public static List<WebRole> WebRole.findAllWebRoles() {
        return entityManager().createQuery("SELECT o FROM WebRole o", WebRole.class).getResultList();
    }
    
    public static List<WebRole> WebRole.findAllWebRoles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WebRole o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WebRole.class).getResultList();
    }
    
    public static WebRole WebRole.findWebRole(Long id) {
        if (id == null) return null;
        return entityManager().find(WebRole.class, id);
    }
    
    public static List<WebRole> WebRole.findWebRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WebRole o", WebRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<WebRole> WebRole.findWebRoleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM WebRole o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, WebRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void WebRole.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void WebRole.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            WebRole attached = WebRole.findWebRole(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void WebRole.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void WebRole.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public WebRole WebRole.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        WebRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}