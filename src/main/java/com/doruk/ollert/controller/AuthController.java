package com.doruk.ollert.controller;

import com.doruk.ollert.auth.TokenManager;
import com.doruk.ollert.dto.AuthDTO;
import com.doruk.ollert.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("auth")
public class AuthController {
    final TokenManager tokenManager;
    final AuthenticationManager authenticationManager;
    final UserService userService;

    public AuthController(TokenManager tokenManager, AuthenticationManager authenticationManager, UserService userService) {
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO auth) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
            return ResponseEntity.ok(tokenManager.generateToken(auth.getUsername()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody AuthDTO auth) {
        try {
            userService.saveUser(auth);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
