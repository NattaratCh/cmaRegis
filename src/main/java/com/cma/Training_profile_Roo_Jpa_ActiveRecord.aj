// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Training_profile;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Training_profile_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Training_profile.entityManager;
    
    public static final List<String> Training_profile.fieldNames4OrderClauseFilter = java.util.Arrays.asList("trainingName", "trainingInstitute", "trainingClass", "trainingYear", "studentProfile");
    
    public static final EntityManager Training_profile.entityManager() {
        EntityManager em = new Training_profile().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Training_profile.countTraining_profiles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Training_profile o", Long.class).getSingleResult();
    }
    
    public static List<Training_profile> Training_profile.findAllTraining_profiles() {
        return entityManager().createQuery("SELECT o FROM Training_profile o", Training_profile.class).getResultList();
    }
    
    public static List<Training_profile> Training_profile.findAllTraining_profiles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Training_profile o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Training_profile.class).getResultList();
    }
    
    public static Training_profile Training_profile.findTraining_profile(Long id) {
        if (id == null) return null;
        return entityManager().find(Training_profile.class, id);
    }
    
    public static List<Training_profile> Training_profile.findTraining_profileEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Training_profile o", Training_profile.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Training_profile> Training_profile.findTraining_profileEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Training_profile o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Training_profile.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Training_profile.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Training_profile.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Training_profile attached = Training_profile.findTraining_profile(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Training_profile.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Training_profile.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Training_profile Training_profile.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Training_profile merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
