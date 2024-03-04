package com.bpb.securecoding.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import org.owasp.esapi.ESAPI;
//import org.owasp.esapi.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestValidatorFilter extends OncePerRequestFilter {
    //private final Logger logger = ESAPI.getLogger("RequestValidatorFilter");

    final ObjectMapper objectMapper;

    public RequestValidatorFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //ESAPI.httpUtilities().setCurrentHTTP(req, res);
        filterChain.doFilter(request,response);
    }
}
