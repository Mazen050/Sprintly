package com.sprintly.backend.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//*
// Logging Filter
// Used for logging every incomming request */
// @Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Request URL: " + request.getRequestURL());
        filterChain.doFilter(request, response);
        System.out.println("Response Status: " + response.getStatus());

    }

}
