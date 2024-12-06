package com.example.javaee.modules.auth;

import com.example.javaee.entities.User;
import com.example.javaee.modules.email.EmailService;
import com.example.javaee.modules.user.UserService;
import com.example.javaee.modules.user.dto.CreateUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;

  public AuthService(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
  }

  public User registerUser(CreateUserDto createUserDto) {
    String hashedPassword = passwordEncoder.encode(createUserDto.getPassword());

    User newUser = new User();
    newUser.setUsername(createUserDto.getUsername());
    newUser.setEmail(createUserDto.getEmail());
    newUser.setPassword(hashedPassword);

    emailService.sendGreetingEmail(createUserDto.getEmail(), "Welcome to my site!", "Thank you for registration! It is great to see you on my site.");

    return userService.saveUser(newUser);
  }
}
