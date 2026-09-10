package com.controller.rest;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationRestController {


    @PostMapping("/sign-up")
    public void signUp() {
    }


    @PostMapping("/sign-in")
    public void signIn() {
    }


    @DeleteMapping("/sign-out")
    public void signOut() {
    }
}
