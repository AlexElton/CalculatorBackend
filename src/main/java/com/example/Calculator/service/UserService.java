package com.example.Calculator.service;

import com.example.Calculator.model.User;
import com.example.Calculator.model.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void register(User user) {
    user.setPassword(passwordEncoder.encoder().encode(user.getPassword()));
    userRepository.save(user);
  }

  public String login(String username, String password) {
    User user = userRepository.findByUsername(username).orElseThrow(ForbiddenException::new);
    if (!passwordEncoder.encoder().matches(password, user.getPassword())) {
      throw new ForbiddenException();
    }

    return JWTService.generateToken(username);
  }

}
