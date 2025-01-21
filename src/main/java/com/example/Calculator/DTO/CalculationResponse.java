package com.example.Calculator.DTO;

public class CalculationResponse {
  private double result;
  private String message;

  public CalculationResponse(double result, String message) {
    this.result = result;
    this.message = message;
  }


  public double getResult () {
    return result;
  }

  public String getMessage () {
    return message;
  }

  public void setResult (double result) {
    this.result = result;
  }
  public void setMessage (String message) {
    this.message = message;
  }

}
