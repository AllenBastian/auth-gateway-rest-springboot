package com.auth.adminms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {


    //authority is checked here and only Admins can access (session set in auth service)
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{

        http.csrf(csrf->csrf.disable()).
                httpBasic(httpBasic->httpBasic.disable()).
                      formLogin(form->form.disable());

        http.authorizeHttpRequests(request->request.requestMatchers("/api/v1/admin/**").
                hasAuthority("ADMIN")
                .anyRequest().authenticated());
        return http.build();


    }
}
