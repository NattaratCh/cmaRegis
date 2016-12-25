package com.cma.web;

import com.cma.Batch;
import com.cma.Student;
import com.cma.UserWeb;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;

/**
 * Created by NATTARAT on 2016-12-22.
 */
public class StudentUtils {
    private static Log log = LogFactory.getLog(StudentUtils.class);
    public static String generateUsername(Student student, Batch batch){
        if(student == null || batch == null){
            return null;
        }else{
            String username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+student.getFirstnameEn().toLowerCase();
            String uniqueUsername = generateUniqueUsername(batch, username.toLowerCase(), student, 1);
            log.info("generateUsername() | uniqueUsername : "+uniqueUsername);
            return uniqueUsername;
        }
    }

    public static String generateUniqueUsername(Batch batch, String username, Student student, int i){ // i =start with 1
        UserWeb userWeb = UserWeb.getUserWeb(username);
        if(userWeb != null){
            String lastname = student.getLastnameEn().substring(0,i);
            username = batch.getCourse().getCode()+(batch.getNumber() != null ? batch.getNumber() : "") +"_"+student.getFirstnameEn().toLowerCase()+lastname;
            username = username.toLowerCase();
            log.info("generateUniqueUsername() | username : "+username+" | i :"+i);
            return generateUniqueUsername(batch, username, student, i+1);
        }else{
            return username;
        }
    }

    public static String bCryptEncode(String password){
        if(password == null){
            return null;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public static Student copyStudent(Student student){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(student);
            oos.flush();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteData = bos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
        Student returnStudent = null;
        try {
            returnStudent = (Student) new ObjectInputStream(bais).readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return returnStudent;
    }
}
