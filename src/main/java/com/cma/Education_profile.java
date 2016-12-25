package com.cma;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.validation.constraints.Pattern;
import java.util.List;


@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Education_profile {
    public Education_profile(){
        super();
    }

    public Education_profile(Education_profile education_profile){
        this.degree = education_profile.degree;
        this.field = education_profile.field;
        this.university = education_profile.university;
        this.graduateYear = education_profile.graduateYear;
    }

    String degree;

    String field;

    String university;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    String graduateYear;

    @ManyToOne
    private Student studentProfile;

    public static List listEducationProfile(Student student){
        try{
            EntityManager entityManager = MapStudent.entityManager();
            String sql = "";
            Query query = null;
            if(student != null){
                query = entityManager.createQuery("select e from Education_profile e where e.studentProfile= :student");
                query.setParameter("student", student);
            }

            List educationProfileList = (List) query.getResultList();
            return educationProfileList;
        }catch (Exception e){
            return null;
        }

    }

    public static boolean deleteEducationProfile(List<Education_profile> education_profiles){
        boolean result = false;
        if(education_profiles != null){
            for(int i=0; i < education_profiles.size(); i++){
                Education_profile education_profile = (Education_profile) education_profiles.get(i);
                education_profile.remove();
            }
            result = true;
        }

        return result;
    }
}
