package com.example.springbootgraphql.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!StringUtils.isEmpty(request.getHeader("Origin"))) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,HEAD,OPTIONS");
            response.addHeader("Access-Control-Max-Age", "5");
            String reqHead = request.getHeader("Access-Control-Request-Headers");
            if (!StringUtils.isEmpty(reqHead)) {
                response.addHeader("Access-Control-Allow-Headers", reqHead);
            }
        }
        if (!request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
        }
    }
}
