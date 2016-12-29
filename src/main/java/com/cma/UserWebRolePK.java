package com.cma;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by PC on 29/12/2559.
 */
@Embeddable
public class UserWebRolePK implements Serializable{
    private UserWeb userWeb;
    private WebRole webRole;

    public UserWebRolePK() {
    }

    public UserWeb getUserWeb() {
        return userWeb;
    }

    public void setUserWeb(UserWeb userWeb) {
        this.userWeb = userWeb;
    }

    public WebRole getRole() {
        return webRole;
    }

    public void setRole(WebRole role) {
        this.webRole = role;
    }

    public UserWebRolePK(UserWeb userWeb, WebRole role) {
        this.userWeb = userWeb;
        this.webRole = role;
    }
}
