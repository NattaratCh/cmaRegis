package com.cma;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Children_profile {

    public Children_profile(){
        super();
    }

    public Children_profile(Children_profile children_profile) {
        this.firstName = children_profile.firstName;
        this.lastName = children_profile.lastName;
        this.career = children_profile.career;
        this.childrenBdateString = children_profile.childrenBdateString;
        this.birthday = children_profile.birthday;
        this.race = children_profile.race;
        this.nationality = children_profile.nationality;
        this.religion = children_profile.religion;

    }

    private String firstName;

    private String lastName;

    private String career;

    private String childrenBdateString;

    @DateTimeFormat(style = "M-")
    private Date birthday;

    private String race;

    private String nationality;

    private String religion;

    @ManyToOne
    private Student studentProfile;

    public static List listChildrenProfile(Student student){
        EntityManager entityManager = MapStudent.entityManager();
        String sql = "";
        Query query = null;
        if(student != null){
            query = entityManager.createQuery("select e from Children_profile e where e.studentProfile= :student");
            query.setParameter("student", student);
        }

        List childrenProfileList = (List) query.getResultList();
        return childrenProfileList;
    }

    public static boolean deleteChildrenProfile(List<Children_profile> children_profiles){
        boolean result = false;
        if(children_profiles != null){
            for(int i=0; i < children_profiles.size(); i++){
                Children_profile children_profile = (Children_profile) children_profiles.get(i);
                children_profile.remove();
            }
            result = true;
        }

        return result;
    }
}
