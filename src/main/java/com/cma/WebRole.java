package com.cma;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "Role")
public class WebRole {
    private String authority;

    public static WebRole getWebRole(String authority){
        EntityManager entityManager = WebRole.entityManager();
        Query query = entityManager.createQuery(" select r from WebRole r where r.authority= :authority ");
        query.setParameter("authority", authority);
        WebRole webRole = (WebRole) query.getSingleResult();
        return webRole;
    }
}
