// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma;

import com.cma.Batch;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Batch_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Batch.entityManager;
    
    public static final List<String> Batch.fieldNames4OrderClauseFilter = java.util.Arrays.asList("nameTh", "nameEn", "start_date", "end_date", "std_profileSet", "file1", "file2", "file3", "file4", "file5", "filename1", "filename2", "filename3", "filename4", "filename5", "uploadfile1", "uploadfile2", "uploadfile3", "uploadfile4", "uploadfile5", "remark", "type", "activityCalendar", "directory", "number", "course");
    
    public static final EntityManager Batch.entityManager() {
        EntityManager em = new Batch().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Batch.countBatches() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Batch o", Long.class).getSingleResult();
    }
    
    public static List<Batch> Batch.findAllBatches() {
        return entityManager().createQuery("SELECT o FROM Batch o", Batch.class).getResultList();
    }
    
    public static List<Batch> Batch.findAllBatches(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Batch o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Batch.class).getResultList();
    }
    
    public static Batch Batch.findBatch(Long id) {
        if (id == null) return null;
        return entityManager().find(Batch.class, id);
    }
    
    public static List<Batch> Batch.findBatchEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Batch o", Batch.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Batch> Batch.findBatchEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Batch o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Batch.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Batch.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Batch.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Batch attached = Batch.findBatch(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Batch.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Batch.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Batch Batch.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Batch merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}