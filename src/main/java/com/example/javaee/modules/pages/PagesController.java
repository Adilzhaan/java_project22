package com.example.javaee.modules.pages;

import com.example.javaee.modules.user.dto.CreateUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {
  @GetMapping("/login")
  public String showLoginPage() {
    return "login"; // Thymeleaf template
  }

  @GetMapping("/register")
  public String showRegisterPage() {
    return "register";
  }

  @GetMapping("/dashboard")
  public String showDashboardPage() {
    return "dashboard";
  }
}
