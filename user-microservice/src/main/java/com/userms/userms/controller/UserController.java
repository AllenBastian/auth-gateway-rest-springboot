package com.userms.userms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/routes")
public class UserController {
    @GetMapping(path = "/get")
    public ResponseEntity<String> getRoute(){
        return ResponseEntity.status(HttpStatus.OK).body("user service get");
    }


    @PostMapping(path = "/post")
    public ResponseEntity<String> PostRoute(){
        return ResponseEntity.status(HttpStatus.OK).body("user service post");
    }
}
