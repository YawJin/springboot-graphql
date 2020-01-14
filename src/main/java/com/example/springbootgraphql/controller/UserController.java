package com.example.springbootgraphql.controller;

import com.example.springbootgraphql.model.api.req.UserAuthenticateReq;
import com.example.springbootgraphql.model.api.resp.UserAuthenticateResp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${user.authenticate.email:}")
    private String email;

    @Value("${user.authenticate.password:}")
    private String password;

    @Value("${jwt.secret:}")
    private String jwtSecret;

    private static final long jwtExpirationTime = 86_400_000; // 24h

    @PostMapping("/authenticate")
    public UserAuthenticateResp authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody UserAuthenticateReq req) {
        UserAuthenticateResp resp = new UserAuthenticateResp();
        if (email.equalsIgnoreCase(req.getEmail()) && password.equalsIgnoreCase(req.getPassword())) {
            resp.setSuccess(true);
            resp.setToken(createToken(request, response, req.getEmail()));
            resp.addMessage("Authentication success.");
        } else {
            resp.addMessage("Authentication failed.");
        }

        return resp;
    }

    private String createToken(HttpServletRequest request, HttpServletResponse response, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,HEAD,OPTIONS");
        response.addHeader("Access-Control-Max-Age", "5");
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        String reqHead = request.getHeader("Access-Control-Request-Headers");
        if (!StringUtils.isEmpty(reqHead)) {
            response.addHeader("Access-Control-Allow-Headers", reqHead);
        }
        return token;
    }
}