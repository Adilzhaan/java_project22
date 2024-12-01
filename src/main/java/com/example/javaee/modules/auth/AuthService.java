package com.example.javaee.modules.auth;

import com.example.javaee.entities.User;
import com.example.javaee.modules.auth.dto.LoginDto;
import com.example.javaee.modules.user.UserService;
import com.example.javaee.modules.user.dto.CreateUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(CreateUserDto createUserDto) {
    String hashedPassword = passwordEncoder.encode(createUserDto.getPassword());

    User newUser = new User();
    newUser.setUsername(createUserDto.getUsername());
    newUser.setEmail(createUserDto.getEmail());
    newUser.setPassword(hashedPassword);

    return userService.saveUser(newUser);
  }

  public boolean authenticateUser(LoginDto loginDto) {
    User user = userService.findUserByUsername(loginDto.getUsername());

    if (user == null) {
      return false;
    }

    return passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
  }
}
