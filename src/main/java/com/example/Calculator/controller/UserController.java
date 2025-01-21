package com.example.Calculator.controller;

import com.example.Calculator.DTO.DetailResponse;
import com.example.Calculator.model.User;
import com.example.Calculator.model.UserRepository;
import com.example.Calculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class UserController {

  UserRepository userRepository;
  UserService userService;

  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<DetailResponse> register(@RequestBody User user) {
    userService.register(user);
    return ResponseEntity.ok(new DetailResponse("User registered", user.getUsername()));
  }

  @PostMapping("/login")
  public ResponseEntity<DetailResponse> login(@RequestBody User user) {
    userService.login(user.getUsername(), user.getPassword());
    return ResponseEntity.ok(new DetailResponse("User logged in", user.getUsername()));
  }
}
