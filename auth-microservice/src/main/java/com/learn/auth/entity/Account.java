package com.learn.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;

@Entity
@JsonComponent
public class Account implements Serializable {

    @Id
    private String id;

    @NotNull(message = "Cannot be empty")
    private String Username;

    @NotNull(message = "Cannot be empty")
    private String password;

    public Account(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
