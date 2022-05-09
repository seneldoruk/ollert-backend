package com.doruk.ollert.controller;

import com.doruk.ollert.auth.TokenManager;
import com.doruk.ollert.dto.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin
public class AuthController {

    @Autowired
    TokenManager tokenManager;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "/auth/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO auth){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
            return ResponseEntity.ok(tokenManager.generateToken(auth.getUsername()));
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }
}
