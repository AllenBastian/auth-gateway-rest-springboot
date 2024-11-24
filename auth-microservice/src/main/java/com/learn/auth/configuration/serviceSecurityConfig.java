package com.learn.auth.configuration;

import com.learn.auth.configuration.service.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class serviceSecurityConfig {


    @Autowired
    @Qualifier("adminUserDetailsService")
    UserDetailsService adminUserDetailsService;

    @Autowired
    @Qualifier("myUserDetailsService")
    UserDetailsService userDetailsService;

    @Bean(name = "adminAuthManager")
    @Primary
    public AuthenticationManager adminAuthManager(){
        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        adminProvider.setUserDetailsService(adminUserDetailsService);
        return new ProviderManager(adminProvider);

    }



    @Bean(name = "userAuthManager")
    public AuthenticationManager userAuthManager(){
        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        adminProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(adminProvider);

    }


}
