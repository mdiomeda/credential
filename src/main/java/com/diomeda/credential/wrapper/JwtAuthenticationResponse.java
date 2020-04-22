package com.diomeda.credential.wrapper;

import java.util.Set;
import com.diomeda.credential.model.Rol;
import com.diomeda.credential.model.UserAccount;

/**
 * 
 * @author mdiomeda
 *
 */
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserAccount user;
    private Set<Rol> roles;

    public JwtAuthenticationResponse(String accessToken, UserAccount user) {
        this.accessToken = accessToken;
        this.user = user;
        roles = user.getRoles();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
