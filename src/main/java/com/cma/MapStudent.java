package com.cma;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class MapStudent {
    @OneToOne
    @NotNull
    private Student initStudent;

    @OneToOne
    private Student revisedStudent;

    @OneToOne
    private Student upToDateStudent;


    public static MapStudent findMapStudent(Student initStudent, Student revisedStudent, Student upToDateStudent){
        EntityManager entityManager = MapStudent.entityManager();
        String sql = "";
        Query query = null;
        if(initStudent != null){
            query = entityManager.createQuery("select m from MapStudent m where m.initStudent= :initStudent");
            query.setParameter("initStudent", initStudent);
        }else if(revisedStudent != null){
            query = entityManager.createQuery("select m from MapStudent m where m.revisedStudent= :revisedStudent");
            query.setParameter("revisedStudent", revisedStudent);
        }else if(revisedStudent != null){
            query = entityManager.createQuery("select m from MapStudent m where m.upToDateStudent= :upToDateStudent");
            query.setParameter("upToDateStudent", upToDateStudent);
        }

        MapStudent mapStudent = (MapStudent) query.getSingleResult();
        return mapStudent;
    }
}
