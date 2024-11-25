package com.learn.auth.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AccountMixin {

    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String Username;

    @JsonProperty("password")
    private String password;

    public abstract String getId();

    public abstract void setId(String id);

    @JsonProperty("username")
    public abstract String getUsername();

    public abstract void setUsername(String username);

    @JsonProperty("password")
    public abstract String getPassword();

    public abstract void setPassword(String password);
}