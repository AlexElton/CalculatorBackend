package com.example.Calculator.controller;

import com.example.Calculator.DTO.CalculationRequest;
import com.example.Calculator.DTO.CalculationResponse;
import com.example.Calculator.model.Calculation;
import com.example.Calculator.model.User;
import com.example.Calculator.model.UserRepository;
import com.example.Calculator.model.CalculationRepository;
import com.example.Calculator.service.CalculationService;
import com.example.Calculator.service.JWTService;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/calculate")  // Changed path for clarity
public class CalculationController {

  private final CalculationService calculationService;
  private final CalculationRepository calculationRepository;
  private final UserRepository userRepository;
  private static final Logger logger = LoggerFactory.getLogger(CalculationController.class);
  private final JWTService jWTService;

  @Autowired
  public CalculationController(CalculationService calculationService, UserRepository userRepository, CalculationRepository calculationRepository,
      JWTService jWTService) {
    this.calculationService = calculationService;
    this.userRepository = userRepository;
    this.calculationRepository = calculationRepository;
    this.jWTService = jWTService;
  }

  @PostMapping("")
  public ResponseEntity<CalculationResponse> calculateGet(@RequestBody CalculationRequest request) {
    String token = request.getToken();
    try {
      // Validate if the token is expired
      if (jWTService.isTokenExpired(token)) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
      }
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new CalculationResponse(0, "Token has expired"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new CalculationResponse(0, "Invalid token"));
    }

    logger.info("Request: value1={}, value2={}, operation={}, username={}",
        request.getOperand1(), request.getOperand2(), request.getOperator(), request.getUsername());

    try {
      double result = calculationService.calculate(request);

      return ResponseEntity.ok(new CalculationResponse(result, "Calculation successful."));
    } catch (IllegalArgumentException e) {
      logger.error("Error during calculation: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new CalculationResponse(0, e.getMessage()));
    } catch (Exception e) {
      logger.error("Unexpected error: {}", e.getMessage());
      return ResponseEntity.status(500).body(new CalculationResponse(0, "Internal server error"));
    }
  }

  @GetMapping("/history")
  public ResponseEntity<List<CalculationResponse>> getCalculationHistory(@RequestParam String token) {
    try {
      // Validate token expiration
      if (jWTService.isTokenExpired(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
      }
      String username = jWTService.extractUsername(token);
      User user = userRepository.findByUsername(username)
          .orElseThrow(() -> new IllegalArgumentException("User not found"));

      List<Calculation> calculations = calculationRepository.findByUser(user);

      List<CalculationResponse> history = calculations.stream()
          .map(calc -> new CalculationResponse(calc.getResult(),
              calc.getOperand1() + " " + calc.getOperator() + " " + calc.getOperand2() + " = " + calc.getResult()))
          .collect(Collectors.toList());

      return ResponseEntity.ok(history);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Collections.emptyList());
    }
  }
}
