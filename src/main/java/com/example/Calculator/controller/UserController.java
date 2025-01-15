package com.example.Calculator.controller;

import com.example.Calculator.model.User;
import com.example.Calculator.model.UserRepository;
import com.example.Calculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173",  allowCredentials = "true")
public class UserController {

  UserRepository userRepository;
  UserService userService;

  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody User user) {
    userService.register(user);
    return ResponseEntity.ok("User registered");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody User user) {
    userService.login(user.getUsername(), user.getPassword());
    return ResponseEntity.ok("User logged in");
  }
}
