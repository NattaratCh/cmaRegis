// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cma.web;

import com.cma.AttachFile;
import com.cma.Batch;
import com.cma.Children_profile;
import com.cma.Education_profile;
import com.cma.Training_profile;
import com.cma.UserRegis;
import com.cma.UserRegisRole;
import com.cma.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<AttachFile, String> ApplicationConversionServiceFactoryBean.getAttachFileToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.AttachFile, java.lang.String>() {
            public String convert(AttachFile attachFile) {
                return new StringBuilder().append(attachFile.getPhotoFileName()).append(' ').append(attachFile.getNamecardFileName()).append(' ').append(attachFile.getIdcardFileName()).append(' ').append(attachFile.getPassportFileName()).toString();
            }
        };
    }
    
    public Converter<Long, AttachFile> ApplicationConversionServiceFactoryBean.getIdToAttachFileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.AttachFile>() {
            public com.cma.AttachFile convert(java.lang.Long id) {
                return AttachFile.findAttachFile(id);
            }
        };
    }
    
    public Converter<String, AttachFile> ApplicationConversionServiceFactoryBean.getStringToAttachFileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.AttachFile>() {
            public com.cma.AttachFile convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), AttachFile.class);
            }
        };
    }
    
    public Converter<Batch, String> ApplicationConversionServiceFactoryBean.getBatchToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.Batch, java.lang.String>() {
            public String convert(Batch batch) {
                return new StringBuilder().append(batch.getNameTh()).append(' ').append(batch.getNameEn()).append(' ').append(batch.getStart_date()).append(' ').append(batch.getEnd_date()).toString();
            }
        };
    }
    
    public Converter<Long, Batch> ApplicationConversionServiceFactoryBean.getIdToBatchConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.Batch>() {
            public com.cma.Batch convert(java.lang.Long id) {
                return Batch.findBatch(id);
            }
        };
    }
    
    public Converter<String, Batch> ApplicationConversionServiceFactoryBean.getStringToBatchConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.Batch>() {
            public com.cma.Batch convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Batch.class);
            }
        };
    }
    
    public Converter<Children_profile, String> ApplicationConversionServiceFactoryBean.getChildren_profileToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.Children_profile, java.lang.String>() {
            public String convert(Children_profile children_profile) {
                return new StringBuilder().append(children_profile.getFirstName()).append(' ').append(children_profile.getLastName()).append(' ').append(children_profile.getCareer()).append(' ').append(children_profile.getChildrenBdateString()).toString();
            }
        };
    }
    
    public Converter<Long, Children_profile> ApplicationConversionServiceFactoryBean.getIdToChildren_profileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.Children_profile>() {
            public com.cma.Children_profile convert(java.lang.Long id) {
                return Children_profile.findChildren_profile(id);
            }
        };
    }
    
    public Converter<String, Children_profile> ApplicationConversionServiceFactoryBean.getStringToChildren_profileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.Children_profile>() {
            public com.cma.Children_profile convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Children_profile.class);
            }
        };
    }
    
    public Converter<Education_profile, String> ApplicationConversionServiceFactoryBean.getEducation_profileToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.Education_profile, java.lang.String>() {
            public String convert(Education_profile education_profile) {
                return new StringBuilder().append(education_profile.getDegree()).append(' ').append(education_profile.getField()).append(' ').append(education_profile.getUniversity()).append(' ').append(education_profile.getGraduateYear()).toString();
            }
        };
    }
    
    public Converter<Long, Education_profile> ApplicationConversionServiceFactoryBean.getIdToEducation_profileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.Education_profile>() {
            public com.cma.Education_profile convert(java.lang.Long id) {
                return Education_profile.findEducation_profile(id);
            }
        };
    }
    
    public Converter<String, Education_profile> ApplicationConversionServiceFactoryBean.getStringToEducation_profileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.Education_profile>() {
            public com.cma.Education_profile convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Education_profile.class);
            }
        };
    }
    
    public Converter<Training_profile, String> ApplicationConversionServiceFactoryBean.getTraining_profileToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.Training_profile, java.lang.String>() {
            public String convert(Training_profile training_profile) {
                return new StringBuilder().append(training_profile.getTrainingName()).append(' ').append(training_profile.getTrainingInstitute()).append(' ').append(training_profile.getTrainingClass()).append(' ').append(training_profile.getTrainingYear()).toString();
            }
        };
    }
    
    public Converter<Long, Training_profile> ApplicationConversionServiceFactoryBean.getIdToTraining_profileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.Training_profile>() {
            public com.cma.Training_profile convert(java.lang.Long id) {
                return Training_profile.findTraining_profile(id);
            }
        };
    }
    
    public Converter<String, Training_profile> ApplicationConversionServiceFactoryBean.getStringToTraining_profileConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.Training_profile>() {
            public com.cma.Training_profile convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Training_profile.class);
            }
        };
    }
    
    public Converter<UserRegis, String> ApplicationConversionServiceFactoryBean.getUserRegisToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.UserRegis, java.lang.String>() {
            public String convert(UserRegis userRegis) {
                return new StringBuilder().append(userRegis.getUsername()).append(' ').append(userRegis.getPassword()).append(' ').append(userRegis.getPassword1()).append(' ').append(userRegis.getPassword2()).toString();
            }
        };
    }
    
    public Converter<Long, UserRegis> ApplicationConversionServiceFactoryBean.getIdToUserRegisConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.UserRegis>() {
            public com.cma.UserRegis convert(java.lang.Long id) {
                return UserRegis.findUserRegis(id);
            }
        };
    }
    
    public Converter<String, UserRegis> ApplicationConversionServiceFactoryBean.getStringToUserRegisConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.UserRegis>() {
            public com.cma.UserRegis convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserRegis.class);
            }
        };
    }
    
    public Converter<UserRegisRole, String> ApplicationConversionServiceFactoryBean.getUserRegisRoleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.cma.UserRegisRole, java.lang.String>() {
            public String convert(UserRegisRole userRegisRole) {
                return new StringBuilder().append(userRegisRole.getRole_name()).toString();
            }
        };
    }
    
    public Converter<Long, UserRegisRole> ApplicationConversionServiceFactoryBean.getIdToUserRegisRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.cma.UserRegisRole>() {
            public com.cma.UserRegisRole convert(java.lang.Long id) {
                return UserRegisRole.findUserRegisRole(id);
            }
        };
    }
    
    public Converter<String, UserRegisRole> ApplicationConversionServiceFactoryBean.getStringToUserRegisRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.cma.UserRegisRole>() {
            public com.cma.UserRegisRole convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserRegisRole.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAttachFileToStringConverter());
        registry.addConverter(getIdToAttachFileConverter());
        registry.addConverter(getStringToAttachFileConverter());
        registry.addConverter(getBatchToStringConverter());
        registry.addConverter(getIdToBatchConverter());
        registry.addConverter(getStringToBatchConverter());
        registry.addConverter(getChildren_profileToStringConverter());
        registry.addConverter(getIdToChildren_profileConverter());
        registry.addConverter(getStringToChildren_profileConverter());
        registry.addConverter(getEducation_profileToStringConverter());
        registry.addConverter(getIdToEducation_profileConverter());
        registry.addConverter(getStringToEducation_profileConverter());
        registry.addConverter(getTraining_profileToStringConverter());
        registry.addConverter(getIdToTraining_profileConverter());
        registry.addConverter(getStringToTraining_profileConverter());
        registry.addConverter(getUserRegisToStringConverter());
        registry.addConverter(getIdToUserRegisConverter());
        registry.addConverter(getStringToUserRegisConverter());
        registry.addConverter(getUserRegisRoleToStringConverter());
        registry.addConverter(getIdToUserRegisRoleConverter());
        registry.addConverter(getStringToUserRegisRoleConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
