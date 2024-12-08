package com.example.springemailexample.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

//
//@RestController
//@RequestMapping("/auth")
//public class AuthService {
//
//        private static final String SECRET_KEY = "your-secret-key";
//
//        @PostMapping("/login")
//        public String login(@RequestParam String username) {
//            String token = Jwts.builder()
//                    .setSubject(username)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
//                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                    .compact();
//            return token;
//        }
//    }
//

