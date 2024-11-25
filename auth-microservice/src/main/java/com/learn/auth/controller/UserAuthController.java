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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

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

        Authentication userAuthentication = UsernamePasswordAuthenticationToken.unauthenticated(
                user.getUsername(),user.getPassword()
        );
        Authentication authenticationResponse = userAuthManager.authenticate(userAuthentication);

        NormalUser normalUser = (NormalUser) authenticationResponse.getPrincipal();

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

        System.out.println(newAuth.getAuthorities());
        context.setAuthentication(newAuth);
        securityContextRepository.saveContext(context,request,response);


        return ResponseEntity.status(HttpStatus.OK).body("user login success");
    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> testController(){
        return ResponseEntity.status(HttpStatus.OK).body("auth-user test");
    }

    @PostMapping(path= "/logout")
    public ResponseEntity<String> logoutController(HttpServletRequest request,HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
            request.getSession().invalidate();
        }

        return ResponseEntity.status(HttpStatus.OK).body("logout success from user");
    }
}
