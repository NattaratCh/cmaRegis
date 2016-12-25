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
public class Training_profile {
    public Training_profile(){
        super();
    }

    public Training_profile(Training_profile training_profile) {
        this.trainingName = training_profile.trainingName;
        this.trainingInstitute = training_profile.trainingInstitute;
        this.trainingClass = training_profile.trainingClass;
        this.trainingYear = training_profile.trainingYear;
    }

    String trainingName;

    String trainingInstitute;

    String trainingClass;

    @Pattern(regexp = "[1-9][0-9][0-9][0-9]|",message = "Invalid Year")
    String trainingYear;

    @ManyToOne
    private Student studentProfile;

    public static List listTrainingProfile(Student student){
        try{
            EntityManager entityManager = MapStudent.entityManager();
            String sql = "";
            Query query = null;
            if(student != null){
                query = entityManager.createQuery("select e from Training_profile e where e.studentProfile= :student");
                query.setParameter("student", student);
            }

            List trainingProfileList = (List) query.getResultList();
            return trainingProfileList;
        }catch (Exception e){
            return null;
        }

    }

    public static boolean deleteTrainingProfile(List<Training_profile> training_profiles){
        boolean result = false;
        if(training_profiles != null){
            for(int i=0; i < training_profiles.size(); i++){
                Training_profile training_profile = (Training_profile) training_profiles.get(i);
                training_profile.remove();
            }
            result = true;
        }

        return result;
    }
}
