package com.diomeda.credential.wrapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 
 * @author mdiomeda
 *
 */
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 1, max = 1)
    private String status;
    
    @NotBlank
    @Size(min = 6, max = 20)
    private String rol;

    @NotNull
    private Long personId;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
