// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.UserWeb;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserWeb_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager UserWeb.entityManager;
    
    public static final List<String> UserWeb.fieldNames4OrderClauseFilter = java.util.Arrays.asList("accountExpired", "accountLocked", "enabled", "lastLogin", "lastUpdate", "loginCount", "oldPasswordList", "password", "passwordExpired", "username");
    
    public static final EntityManager UserWeb.entityManager() {
        EntityManager em = new UserWeb().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long UserWeb.countUserWebs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UserWeb o", Long.class).getSingleResult();
    }
    
    public static List<UserWeb> UserWeb.findAllUserWebs() {
        return entityManager().createQuery("SELECT o FROM UserWeb o", UserWeb.class).getResultList();
    }
    
    public static List<UserWeb> UserWeb.findAllUserWebs(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UserWeb o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UserWeb.class).getResultList();
    }
    
    public static UserWeb UserWeb.findUserWeb(Long id) {
        if (id == null) return null;
        return entityManager().find(UserWeb.class, id);
    }
    
    public static List<UserWeb> UserWeb.findUserWebEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UserWeb o", UserWeb.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<UserWeb> UserWeb.findUserWebEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UserWeb o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UserWeb.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void UserWeb.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void UserWeb.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UserWeb attached = UserWeb.findUserWeb(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void UserWeb.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void UserWeb.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public UserWeb UserWeb.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UserWeb merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}