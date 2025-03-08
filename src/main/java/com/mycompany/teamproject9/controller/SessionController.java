package com.mycompany.teamproject9.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/info")
    public ResponseEntity<?> getSessionInfo(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.ok(Map.of("message", "세션이 없습니다. 로그인하세요."));
        }

        String username = authentication.getName();
        String roles = authentication.getAuthorities().toString();

        return ResponseEntity.ok(Map.of(
            "message", "세션 유지 중!",
            "username", username,
            "roles", roles
        ));
    }
}

