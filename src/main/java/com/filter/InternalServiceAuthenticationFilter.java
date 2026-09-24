package com.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class InternalServiceAuthenticationFilter extends OncePerRequestFilter {

    @Value("${internal.service.secret}")
    private String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        if (!request.getRequestURI().startsWith("/api/internal/")) {
            filterChain.doFilter(request, response);
            return;
        }


        String providedSecretKey = request.getHeader("X-Internal-Service-Key");

        if(providedSecretKey == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!MessageDigest.isEqual(
                secretKey.getBytes(StandardCharsets.UTF_8),
                providedSecretKey.getBytes(StandardCharsets.UTF_8))
        ) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("INTERNAL_SERVER");

        var token = new UsernamePasswordAuthenticationToken(
                "internal-service",
                null,
                Collections.singleton(authority)
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
