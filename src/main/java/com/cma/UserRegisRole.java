package com.cma;

import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UserRegisRole {

    @NotNull
    private String role_name;

    public static UserRegisRole findUserRegisRoleByRoleName(String roleName) {
        EntityManager entityManager = UserRegisRole.entityManager();
        Query query = entityManager.createQuery("select x from UserRegisRole x WHERE x.role_name= :roleName");
        query.setParameter("roleName", roleName);
        UserRegisRole userRegisRole = (UserRegisRole) query.getSingleResult();
        return userRegisRole;
    }

}
