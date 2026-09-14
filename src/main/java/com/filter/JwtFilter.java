package com.filter;

import com.dto.UserReadDto;
import com.exception.NotValidJwtTokenException;
import com.service.JwtService;
import com.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request.getHeader("Authorization"));

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtService.extractClaims(token);


        String email = claims.getSubject();
        if(!checkTokenSubject(email)) {
            throw new NotValidJwtTokenException("token is not valid");
        }

        Date expirationDate = claims.getExpiration();
        if(!checkExpireDate(expirationDate)) {
            throw new NotValidJwtTokenException("token is expired");
        }

        new UsernamePasswordAuthenticationToken(
                email,
                null
        );


//        SecurityContextHolder.getContext().setAuthentication(authenticaion);//TODO: сделать

        filterChain.doFilter(request, response);
    }


    private String getToken(String authorization) {
        if(authorization == null || !authorization.contains("Bearer ")) {
            return null;
        }

        return authorization.substring(7);
    }


    private boolean checkExpireDate(Date expirationDate) {
        Date now = new Date(System.currentTimeMillis());

        return !expirationDate.before(now);
    }


    private boolean checkTokenSubject(String subject) {
        UserReadDto userByEmail = userService.findUserByEmail(subject);

        if(userByEmail == null) {
            return false;
        }

        return true;
    }
}
