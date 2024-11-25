package com.learn.auth.controller;


import com.learn.auth.configuration.principal.NormalUser;
import com.learn.auth.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth/user")
public class UserAuthController {

    @Autowired
    @Qualifier("userAuthManager")
    AuthenticationManager userAuthManager;


    SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginController(@RequestBody Account user, HttpServletRequest request,
                                                  HttpServletResponse response){

        Authentication adminAuthentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticationResponse = userAuthManager.authenticate(adminAuthentication);

        NormalUser normalUser = (NormalUser) adminAuthentication.getPrincipal();

        //new principal
        User principal = new User(
                normalUser.getUsername(),
                normalUser.getPassword(),
                normalUser.isEnabled(),
                normalUser.isAccountNonExpired(),
                normalUser.isCredentialsNonExpired(),
                normalUser.isAccountNonLocked(),
                normalUser.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(principal,authenticationResponse.getCredentials(),
                authenticationResponse.getAuthorities());

        context.setAuthentication(newAuth);
        securityContextRepository.saveContext(context,request,response);


        return ResponseEntity.status(HttpStatus.OK).body("check");
    }
}
