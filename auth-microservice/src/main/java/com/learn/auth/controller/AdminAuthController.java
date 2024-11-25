package com.learn.auth.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.auth.configuration.principal.AdminUser;
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
@RequestMapping(path = "/api/v1/auth/admin")
public class AdminAuthController {

    @Autowired
    ObjectMapper objectMapper;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    @Qualifier("adminAuthManager")
    AuthenticationManager adminAuthManager;

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginController(@RequestBody Account admin, HttpServletRequest request,
                                                  HttpServletResponse response) throws  Exception{


        //creates a unauthenticated object for giving to DAOauthprovider
        Authentication adminAuthentication = UsernamePasswordAuthenticationToken.
                unauthenticated(admin.getUsername(),admin.getPassword());


        //authenticate using admin auth manager
        Authentication authenticationResponse = adminAuthManager.
                authenticate(adminAuthentication);


        //get principal which is usually a userDetails object to admin user
        AdminUser adminUser = (AdminUser) authenticationResponse.getPrincipal();
        SecurityContext context = SecurityContextHolder.createEmptyContext();


        //create a new principal with default security class User
        User user = new User(
                adminUser.getUsername(),
                adminUser.getPassword(),
                adminUser.isEnabled(),
                adminUser.isAccountNonExpired(),
                adminUser.isCredentialsNonExpired(),
                adminUser.isAccountNonLocked(),
                adminUser.getAuthorities()
        );

        //create new authentication object
        Authentication newAuth = new UsernamePasswordAuthenticationToken
                (user,authenticationResponse.getCredentials(),
                        authenticationResponse.getAuthorities());

        context.setAuthentication(newAuth);
        System.out.println(newAuth.getAuthorities());
        System.out.println(request.getSession().getId());
        securityContextRepository.saveContext(context,request,response);
        return ResponseEntity.status(HttpStatus.OK).body("check");
    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> testController(){
        return ResponseEntity.status(HttpStatus.OK).body("testing");
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logoutController(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.getSession().invalidate();

        }

        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
