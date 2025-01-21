package com.example.Calculator.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Calculation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne // Many calculation logs can be linked to one user
  @JoinColumn(name = "user_id") // Foreign key column name
  private User user;

  private double operand1;
  private double operand2;
  private String operator;
  private double result;

  @Column(nullable = false, updatable = false)
  private LocalDateTime timestamp;

  // Default constructor
  public Calculation() {
    this.timestamp = LocalDateTime.now();
  }

  public Calculation(double operand1, double operand2, String operator, double result, User user) {
    this.operand1 = operand1;
    this.operand2 = operand2;
    this.operator = operator;
    this.result = result;
    this.timestamp = LocalDateTime.now();
    this.user = user;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public double getResult() {
    return result;
  }

  public void setResult(double result) {
    this.result = result;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
