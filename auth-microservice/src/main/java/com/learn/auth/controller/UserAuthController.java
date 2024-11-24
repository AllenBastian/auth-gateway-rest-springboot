package com.learn.auth.controller;


import com.learn.auth.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user/auth")
public class UserAuthController {

    @Autowired
    @Qualifier("userAuthManager")
    AuthenticationManager userAuthManager;

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginController(@RequestBody Account admin){

        Authentication adminAuthentication = new UsernamePasswordAuthenticationToken(admin.getUsername(),admin.getPassword());
        Authentication authenticationResponse = userAuthManager.authenticate(adminAuthentication);
        return ResponseEntity.status(HttpStatus.OK).body("check");
    }
}
