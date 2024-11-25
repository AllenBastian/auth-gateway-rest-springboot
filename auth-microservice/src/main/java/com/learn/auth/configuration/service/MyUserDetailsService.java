package com.learn.auth.configuration.service;

import com.learn.auth.configuration.principal.NormalUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals("user")){
            throw new UsernameNotFoundException("not found");
        }

        return new NormalUser();
    }
}
