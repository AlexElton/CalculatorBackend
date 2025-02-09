package com.example.Calculator.DTO;

public class CalculationRequest {
  private double operand1;
  private double operand2;
  private String operator;
  private String username;
  private String token;


  public double getOperand1() {
    return operand1;
  }

  public void setOperand1(double operand1) {
    this.operand1 = operand1;
  }

  public double getOperand2() {
    return operand2;
  }

  public void setOperand2(double operand2) {
    this.operand2 = operand2;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
