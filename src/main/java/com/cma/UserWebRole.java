package com.cma;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", identifierField = "user_id")
public class UserWebRole implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    @GeneratedValue
    private UserWeb userWeb;

    @OneToOne
    @JoinColumn(name = "role_id")
    private WebRole webRole;

    public static UserWebRole getUserWebRole(UserWeb userWeb){
        try{
            EntityManager entityManager = UserWebRole.entityManager();
            String sql = "";
            Query query = null;
            if(userWeb != null){
                query = entityManager.createQuery("select u from UserWebRole u where u.userWeb= :userWeb");
                query.setParameter("userWeb", userWeb);
            }

            UserWebRole userWebRole = (UserWebRole)query.getSingleResult();
            return userWebRole;
        }catch (Exception e){
            return null;
        }

    }
}
