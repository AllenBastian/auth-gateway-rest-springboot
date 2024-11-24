package com.auth.adminms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{

        http.csrf(csrf->csrf.disable()).
                httpBasic(httpBasic->httpBasic.disable()).
                      formLogin(form->form.disable());

        http.authorizeHttpRequests(request->request.anyRequest().permitAll());
        return http.build();


    }
}
