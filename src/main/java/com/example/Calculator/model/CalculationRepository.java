package com.example.Calculator.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
  List<Calculation> findByUser(User user);
}

