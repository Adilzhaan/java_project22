package com.example.javaee.modules.auth;

import com.example.javaee.modules.auth.dto.LoginDto;
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

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody CreateUserDto createUserDto) {
    try {
      authService.registerUser(createUserDto);
      return ResponseEntity.ok("User registered successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Registration failed");
    }
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginDto loginDto, Model model) {
    boolean authenticated = authService.authenticateUser(loginDto);

    if (authenticated) {
      return "redirect:/dashboard";
    } else {
      model.addAttribute("errorMessage", "Invalid username or password.");
      return "login";
    }
  }

  @GetMapping("/me")
  public ResponseEntity<Object> getCurrentUser() {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(principal);
  }
}
