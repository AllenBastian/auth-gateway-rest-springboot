package com.learn.auth.json;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public abstract  class AdminUserMixin {


        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;

        @JsonIgnore
        abstract Collection<? extends GrantedAuthority> getAuthorities();

        @JsonIgnore
        abstract boolean isAccountNonExpired();

        @JsonIgnore
        abstract boolean isAccountNonLocked();

        @JsonIgnore
        abstract boolean isCredentialsNonExpired();

        @JsonIgnore
        abstract boolean isEnabled();
    }


