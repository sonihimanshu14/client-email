package com.example.springemailexample.controller;


import com.example.springemailexample.dto.AuthDto;
import com.example.springemailexample.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody AuthDto request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return jwtService.generateToken(authenticate.getName());
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid or missing Authorization header");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        String username;
        try {
            username = jwtService.extractUsername(token);
            if (jwtService.validateToken(token, username)) {
                return ResponseEntity.ok("Token is valid for user: " + username);
            } else {
                return ResponseEntity.status(401).body("Token is invalid or expired");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token validation failed: " + e.getMessage());
        }
    }
}

