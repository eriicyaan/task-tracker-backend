package com.filter;

import com.entity.UserRole;
import com.exception.NotValidJwtTokenException;
import com.service.JwtService;
import com.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie authorizationCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("jwt_token"))
                .findFirst()
                .orElse(null);

        if (authorizationCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationCookie.getValue();

        Claims claims;
        try {
            claims = jwtService.extractClaims(token);
        } catch (ExpiredJwtException ex) {
            throw new NotValidJwtTokenException("token is expired");
        } catch (Exception ex) {
            throw new NotValidJwtTokenException("token is not valid");
        }

        String username = claims.getSubject();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

}
