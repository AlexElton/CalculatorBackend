package com.example.Calculator.service;

import com.example.Calculator.model.User;
import com.example.Calculator.model.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User register(User user) {
    //Encrypt password
    user.setPassword(user.getPassword());
    return userRepository.save(user);
  }

  public User login(String username, String password) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    if (!user.getPassword().equals(password)) {
        throw new RuntimeException("Invalid password");
      }

    return user;
  }

}
