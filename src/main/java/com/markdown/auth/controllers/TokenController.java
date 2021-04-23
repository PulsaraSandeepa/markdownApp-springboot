package com.markdown.auth.controllers;

import com.markdown.auth.services.TokenService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/validate")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception{

        String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String token = null;
        if(!isEmpty(authHeader)){
            token = authHeader.split("\\s")[1];
        }

        tokenService.validateToken(token);
    }

}
