package com.filter;

import com.dto.UserReadDto;
import com.exception.NotValidJwtTokenException;
import com.service.JwtService;
import com.service.UserService;
import io.jsonwebtoken.Claims;
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
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

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

        String token = authorizationCookie == null
                ? null
                : authorizationCookie.getValue();

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims;
        try {
            claims = jwtService.extractClaims(token);
        } catch (Exception ex) {
            throw new NotValidJwtTokenException("signature exception of token");
        }

        String username = claims.getSubject();
        if (!checkTokenSubject(username)) {
            throw new NotValidJwtTokenException("token is not valid");
        }

        Date expirationDate = claims.getExpiration();
        if (!checkExpireDate(expirationDate)) {
            throw new NotValidJwtTokenException("token is expired");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }


    private boolean checkExpireDate(Date expirationDate) {
        Date now = new Date(System.currentTimeMillis());

        return !expirationDate.before(now);
    }


    private boolean checkTokenSubject(String subject) {
        UserReadDto userByEmail = userService.findUserByUsername(subject);

        return userByEmail != null;
    }
}
