package com.protal.myApp;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderFilter extends OncePerRequestFilter {

    public HeaderFilter(){}

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, content-type");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}