package com.cma.common;

import com.cma.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cma.common.authenManager.findUserloginByname;

public class cmaUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserRegis userRegis = findUserloginByname(username);
        if(userRegis ==null){
            throw new UsernameNotFoundException("username is not found");
        }
        else {
            /*System.out.println(">>>>>>>>>username "+userlogin.getUsername());*/
            boolean accountNonExpired;
            if(userRegis.getUserRole().getRole_name().equals("ROLE_ADMIN")|| userRegis.getUserRole().getRole_name().equals("ROLE_ADMIN_EXPIRED")){
                accountNonExpired = true;
            }
            else {
                accountNonExpired = userRegis ==null?false:checkExpiredDate(userRegis);
            }
            boolean accountNonLocked = true;
            boolean credentialsNonExpired = true;
            boolean enabled = userRegis.getEnabled();
            /*System.out.println(">>>>>>>>>role ->"+userlogin.getUserRole().getRole_name());*/

            List roles = new ArrayList();
            roles.add(userRegis.getUserRole().getRole_name());
            return new User(userRegis.getUsername(),
                    userRegis.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getGrantedAuthorities(roles)
            );
        }

    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public static boolean checkExpiredDate(UserRegis userRegis){

        Batch studentClass = userRegis.getStudentProfile().getStudentClass();

        System.out.println("class ID : "+studentClass.getId());
        Date start_date= studentClass.getStart_date();
        Date end_date= studentClass.getEnd_date();
        end_date.setHours(23);
        end_date.setMinutes(59);
        end_date.setSeconds(59);
        Date current_date = new Date();
        System.out.println("--->cur = "+current_date.toString()+" startdate = "+start_date+" enddate = "+end_date);
        if(current_date.compareTo(start_date)>=0&&current_date.compareTo(end_date)<=0) {
            System.out.println("--->you are in times");
            return true;
        }
        System.out.println("--->out of times, your account is expire.");
        return false;
    }

    /*public static List findRoleOfUser(Long user_id){
        List roles =new ArrayList();
        List arList = Assigned_role.findAllAssigned_roles();
        for(int i=0;i<arList.size();i++){
            Assigned_role ar = (Assigned_role)arList.get(i);
            if(ar.getUser_id()==user_id){
                roles.add(Userrole.findUserrole(ar.getRole_id()).getRole_name());
            }
        }

        return roles;
    }*/
}
