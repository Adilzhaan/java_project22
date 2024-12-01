package com.example.javaee.modules.auth;

import com.example.javaee.modules.user.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/login")
  public String showLoginPage() {
    return "login";
  }

  @GetMapping("/register")
  public String showRegisterPage() {
    return "register";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute CreateUserDto createUserDto, Model model) {
    try {
      authService.registerUser(createUserDto);
      model.addAttribute("successMessage", "User registered successfully! Please log in.");
      return "redirect:/api/auth/login";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
      return "register";
    }
  }


  @GetMapping("/me")
  public ResponseEntity<Object> getCurrentUser() {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(principal);
  }
}
