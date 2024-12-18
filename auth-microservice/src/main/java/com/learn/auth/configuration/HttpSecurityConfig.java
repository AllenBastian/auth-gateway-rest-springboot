package com.learn.auth.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    @Qualifier("adminAuthManager")
    AuthenticationManager adminAuthManager;

    @Autowired
    @Qualifier("userAuthManager")
    AuthenticationManager userAuthManager;



    /*the hasAuthority method is called here in auth service just to test and understand no need for this
        since all are routed through gateway
     */

    @Bean
    public SecurityFilterChain adminConfig(HttpSecurity http) throws Exception{

        http.httpBasic(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
               http.securityMatcher("/api/v1/auth/admin/**").authenticationManager(adminAuthManager).
                       authorizeHttpRequests((requests)->(requests.
                               requestMatchers("/api/v1/auth/admin/login")).
                               permitAll()
                        .requestMatchers("/api/v1/auth/admin/**").hasAuthority("ADMIN").
                               anyRequest().authenticated());

        return http.build();

    }

    @Bean
    public SecurityFilterChain userConfig(HttpSecurity http) throws Exception{

        http.httpBasic(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.securityMatcher("/api/v1/auth/user/**").authenticationManager(userAuthManager).
                authorizeHttpRequests((requests)->(requests.requestMatchers("/api/v1/auth/user/login")).
                        permitAll().requestMatchers("/api/v1/auth/user/**").hasAuthority("USER")
                        .anyRequest().authenticated());

        return http.build();

    }


}
