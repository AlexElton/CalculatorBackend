package com.example.Calculator.controller;

import com.example.Calculator.service.JWTService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JWTService jwtService;

  public AuthController(JWTService jwtService) {
    this.jwtService = jwtService;
  }

  @PostMapping("/validate")
  public ResponseEntity<Map<String, Boolean>> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
    String token = authorizationHeader.replace("Bearer ", "");
    boolean isValid = false;
    try {
      String username = jwtService.extractUsername(token);
      isValid = jwtService.isTokenValid(token, username); // Validate token and username
    } catch (Exception e) {
      isValid = false;
    }
    return ResponseEntity.ok(Map.of("valid", isValid));
  }
}
