package com.sprintly.backend.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sprintly.backend.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


//*
// Authentication Filter
// Checks Authentication of every incomming request */
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var jwtToken = authHeader.replace("Bearer ", "");
            if (!jwtService.validateToken(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }
            var authentication = new UsernamePasswordAuthenticationToken(
                jwtService.getIdFromToken(jwtToken),
                null,
                null
            );
            authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request) // Adds details like IP address, session ID, etc.
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication); // stores the authentication in the security context

            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }

}
