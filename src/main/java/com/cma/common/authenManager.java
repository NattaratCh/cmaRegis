package com.cma.common;

import com.cma.UserRegis;

import java.security.MessageDigest;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static org.apache.commons.lang3.StringUtils.leftPad;

/**
 * Created with IntelliJ IDEA.
 * User: PIPATNG
 * Date: 17/1/2556
 * Time: 11:35 น.
 * To change this template use File | Settings | File Templates.
 */
public class authenManager {
    public static String genPassword(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = length; i > 0; i -= 12) {
            int n = min(12, abs(i));
            sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
        }
        Double randomPosition = random()*(length-1);
        Double randomCapital = random()*25+65;
        char capital = (char)randomCapital.intValue();
        sb.setCharAt(randomPosition.intValue(),capital);
        System.out.println("Password = "+sb.toString());
        return sb.toString();
    }


    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean validateStrongPassword(String password){
        int capitalValid = 0;
        int numberValid = 0;
        int spacialCharacterValid = 0;
        int normalCharacterValid = 0;

        for(int i=0 ;i < password.length();i++){
            char x = password.charAt(i);


            if(x>='0'&&x<='9'){
                numberValid=1;
                /*System.out.println(">>>>>>>validateStrongPassword numberValid pass");*/
            }
            if(x>='a'&&x<='z'){
                normalCharacterValid=1;
                /*System.out.println(">>>>>>>validateStrongPassword capitalValid pass");*/
            }
            if(x>='A'&&x<='Z'){
                capitalValid=1;
                /*System.out.println(">>>>>>>validateStrongPassword capitalValid pass");*/
            }
            if(x=='@'||x=='#'||x=='$'||x=='!'||x=='&'){
                spacialCharacterValid=1;
                /*System.out.println(">>>>>>>validateStrongPassword spacialCharacterValid pass");*/
            }

        }
        int result = numberValid+spacialCharacterValid+capitalValid+normalCharacterValid;
        if(result>=3)
            return true;
        else
            return false;
    }

    public static UserRegis findUserloginByname(String username){
        List userlogin = UserRegis.findAllUserRegises();
        int n = userlogin.size();
        for(int i=0;i<n;i++){
            UserRegis a_userRegis = (UserRegis)userlogin.get(i);

            if(a_userRegis.getUsername().equals(username)){
                return a_userRegis;
            }
        }
        return null;
    }
}
