package com.controller.rest;


import com.dto.UserCreateEditDto;
import com.dto.UserSignInDto;
import com.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        setJwtCookie(token, response);
    }


    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public void signIn(@RequestBody @Validated UserSignInDto user,
                       HttpServletResponse response) {

        String token = authenticationService.signIn(user);
        setJwtCookie(token, response);
    }


    @DeleteMapping("/sign-out")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(HttpServletResponse response) {
        Cookie cookie = createCookie(null);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }


    private void setJwtCookie(String token, HttpServletResponse response) {
        Cookie cookie = createCookie(token);
        response.addCookie(cookie);
    }


    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}
