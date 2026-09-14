package com.controller.rest;


import com.dto.UserCreateEditDto;
import com.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Validated UserCreateEditDto user,
                       HttpServletResponse response) {

        String token = authenticationService.signUp(user);

        response.addHeader("Authorization","Bearer " + token);
    }


    @PostMapping("/sign-in")
    public void signIn() {
    }


    @DeleteMapping("/sign-out")
    public void signOut() {
    }
}
