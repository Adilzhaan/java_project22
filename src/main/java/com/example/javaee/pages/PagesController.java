package com.example.javaee.pages;

import com.example.javaee.entities.Category;
import com.example.javaee.entities.Task;
import com.example.javaee.modules.category.CategoryService;
import com.example.javaee.modules.category.dto.CreateCategoryDto;
import com.example.javaee.modules.task.TaskService;
import com.example.javaee.modules.task.dto.CreateTaskDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class PagesController {
  private final TaskService taskService;
  private final CategoryService categoryService;

  public PagesController(TaskService taskService, CategoryService categoryService) {
    this.taskService = taskService;
    this.categoryService = categoryService;
  }

  @GetMapping("/dashboard")
  public String showDashboardPage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    List<Task> tasks = taskService.getTasksForUser(username);
    List<Category> categories = categoryService.getCategoriesForUser(username);

    model.addAttribute("username", username);
    model.addAttribute("tasks", tasks);
    model.addAttribute("categories", categories);

    return "dashboard";
  }

  @GetMapping("/dashboard/tasks/add")
  public String showCreateTaskPage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    List<Category> categories = categoryService.getCategoriesForUser(username);

    model.addAttribute("createTaskDto", new CreateCategoryDto());
    model.addAttribute("categories", categories);

    return "add-task";
  }


  @PostMapping("/tasks/add")
  public String addTask(@ModelAttribute CreateTaskDto createTaskDto, Authentication authentication, Model model) {
    try {
      taskService.createTask(createTaskDto, authentication.getName());
      model.addAttribute("successMessage", "Task added successfully!");
      return "redirect:/dashboard";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Failed to add task: " + e.getMessage());
      return "add-task";
    }
  }

  @GetMapping("/dashboard/categories/add")
  public String showCreateCategoryPage(Model model) {
    model.addAttribute("createCategoryDto", new CreateCategoryDto());
    return "add-category";
  }

  @PostMapping("/categories/add")
  public String addCategory(@ModelAttribute CreateCategoryDto createCategoryDto, Authentication authentication, Model model) {
    try {
      categoryService.createCategory(createCategoryDto, authentication.getName());
      model.addAttribute("successMessage", "Category added successfully!");
      return "redirect:/dashboard";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Failed to add category: " + e.getMessage());
      return "add-category";
    }
  }
}
