package com.example.javaee.modules.user;

import com.example.javaee.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public User findUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }
}
