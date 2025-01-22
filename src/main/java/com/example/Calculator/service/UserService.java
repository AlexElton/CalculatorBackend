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

  public void register(User user) {
    //Encrypt password
    user.setPassword(user.getPassword());
    userRepository.save(user);
  }

  public String login(String username, String password) {
    User user = userRepository.findByUsername(username).orElseThrow(ForbiddenException::new);
    if (!user.getPassword().equals(password)) {
        throw new ForbiddenException();
      }

    return JWTService.generateToken(username);
  }

}
