package com.example.javaee.pages;

import com.example.javaee.entities.Category;
import com.example.javaee.entities.Task;
import com.example.javaee.modules.category.CategoryService;
import com.example.javaee.modules.category.dto.CreateCategoryDto;
import com.example.javaee.modules.task.TaskService;
import com.example.javaee.modules.task.dto.CreateTaskDto;
import com.example.javaee.modules.task.dto.UpdateTaskDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    model.addAttribute("createTaskDto", new CreateTaskDto());
    model.addAttribute("categories", categories);

    return "add-task";
  }


  @PostMapping("/dashboard/tasks/add")
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

  @GetMapping("/dashboard/tasks/edit/{id}")
  public String showEditTaskPage(@PathVariable String id, Authentication authentication, Model model) {
    try {
      String username = authentication.getName();
      Task task = taskService.getTaskByIdAndUser(id, username);
      List<Category> categories = categoryService.getCategoriesForUser(username);

      UpdateTaskDto updateTaskDto = new UpdateTaskDto();
      updateTaskDto.setId(task.getId());
      updateTaskDto.setTitle(task.getTitle());
      updateTaskDto.setDescription(task.getDescription());
      updateTaskDto.setDueDate(task.getDueDate().toString());
      updateTaskDto.setStatus(task.getStatus());
      updateTaskDto.setPriority(task.getPriority());
      updateTaskDto.setCategoryId(task.getCategory().getId());

      model.addAttribute("updateTaskDto", updateTaskDto);
      model.addAttribute("categories", categories);

      return "edit-task";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Failed to load task: " + e.getMessage());
      return "redirect:/dashboard";
    }
  }

  @PatchMapping("/dashboard/tasks/edit/{id}")
  public String editTask(@ModelAttribute UpdateTaskDto updateTaskDto, @PathVariable String id, Authentication authentication, Model model) {
    try {
      taskService.updateTask(id, updateTaskDto, authentication.getName());
      model.addAttribute("successMessage", "Task edited successfully!");
      return "redirect:/dashboard";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Failed to edit task: " + e.getMessage());
      return "edit-task";
    }
  }

  @DeleteMapping("/dashboard/tasks/delete/{id}")
  public String deleteTask(
    @PathVariable String id,
    Authentication authentication,
    Model model
  ) {
    try {
      String username = authentication.getName();
      taskService.deleteTask(id, username);
      return "redirect:/dashboard";
    } catch (Exception e) {
      model.addAttribute("errorMessage", "Failed to delete task: " + e.getMessage());
      return "dashboard";
    }
  }

  @GetMapping("/dashboard/categories/add")
  public String showCreateCategoryPage(Model model) {
    model.addAttribute("createCategoryDto", new CreateCategoryDto());
    return "add-category";
  }

  @PostMapping("/dashboard/categories/add")
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
