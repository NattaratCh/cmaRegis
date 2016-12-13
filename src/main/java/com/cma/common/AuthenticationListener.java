package com.cma.common;

import com.cma.UserRegis;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import static com.cma.common.authenManager.findUserloginByname;

public class AuthenticationListener implements ApplicationListener <AbstractAuthenticationEvent>
{
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent)
    {
        if (appEvent instanceof AuthenticationSuccessEvent)
        {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
            String username = appEvent.getAuthentication().getName();
            UserRegis userRegis = findUserloginByname(username);
            userRegis.setLoginTimes(0);
            userRegis.merge();
            // add code here to handle successful login event
        }

        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent)
        {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
            System.out.println(">>>unsuccessfull login "+appEvent.getAuthentication().getName());
            String username = appEvent.getAuthentication().getName();
            UserRegis userRegis = findUserloginByname(username);
            if(userRegis !=null){
                int failureTimes = userRegis.getLoginTimes();
                if(failureTimes==4){
                  System.out.println(">>>continuous failed login 5 times");
                  userRegis.setEnabled(false);
                  userRegis.setLoginTimes(0);
                }
                else userRegis.setLoginTimes(failureTimes+1);

                userRegis.merge();
            }
            // add code here to handle unsuccessful login event
            // for example, counting the number of login failure attempts and storing it in db
            // this count can be used to lock or disable any user account as per business requirements
        }
    }
}
