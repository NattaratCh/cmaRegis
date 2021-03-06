// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.UserWebRole;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserWebRole_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager UserWebRole.entityManager;
    
    public static final List<String> UserWebRole.fieldNames4OrderClauseFilter = java.util.Arrays.asList("userWeb", "webRole");
    
    public static final EntityManager UserWebRole.entityManager() {
        EntityManager em = new UserWebRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long UserWebRole.countUserWebRoles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UserWebRole o", Long.class).getSingleResult();
    }
    
    public static List<UserWebRole> UserWebRole.findAllUserWebRoles() {
        return entityManager().createQuery("SELECT o FROM UserWebRole o", UserWebRole.class).getResultList();
    }
    
    public static List<UserWebRole> UserWebRole.findAllUserWebRoles(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UserWebRole o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UserWebRole.class).getResultList();
    }
    
    public static UserWebRole UserWebRole.findUserWebRole(Long id) {
        if (id == null) return null;
        return entityManager().find(UserWebRole.class, id);
    }
    
    public static List<UserWebRole> UserWebRole.findUserWebRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UserWebRole o", UserWebRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<UserWebRole> UserWebRole.findUserWebRoleEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM UserWebRole o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, UserWebRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void UserWebRole.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void UserWebRole.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UserWebRole attached = UserWebRole.findUserWebRole(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void UserWebRole.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void UserWebRole.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public UserWebRole UserWebRole.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UserWebRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
