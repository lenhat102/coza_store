package com.cybersoft.cozastore.payload.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SignupRequest {

    private String username;

    @NotNull
    @NotEmpty
    @Length(min=8)
    private String password;

    @NotNull(message = "email not null")
    @NotEmpty(message = "email not empty")
    @Email(message = "email invalid format")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
