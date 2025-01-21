package com.example.Calculator.service;

import com.example.Calculator.DTO.CalculationRequest;
import com.example.Calculator.model.Calculation;
import com.example.Calculator.model.CalculationRepository;
import com.example.Calculator.model.User;
import com.example.Calculator.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

  @Autowired
  private CalculationRepository calculationRepository;

  @Autowired
  private UserRepository userRepository;  // Inject the user repository

  public double calculate(CalculationRequest request) {
    double operand1 = request.getOperand1();
    double operand2 = request.getOperand2();
    String operator = request.getOperator();

    String username = request.getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    double result = switch (operator) {
      case "+" -> operand1 + operand2;
      case "-" -> operand1 - operand2;
      case "*" -> operand1 * operand2;
      case "/" -> {
        if (operand2 == 0) {
          throw new IllegalArgumentException("Kan ikke dele på 0");
        }
        yield operand1 / operand2;
      }
      default -> throw new IllegalArgumentException("Ugyldig operator: " + operator);
    };

    Calculation calculation = new Calculation(operand1, operand2, operator, result, user);
    calculationRepository.save(calculation);

    return result;
  }
}
