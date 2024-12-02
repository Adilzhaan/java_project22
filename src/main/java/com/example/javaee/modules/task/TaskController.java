package com.example.javaee.modules.task;

import com.example.javaee.entities.Task;
import com.example.javaee.modules.task.dto.CreateTaskDto;
import com.example.javaee.modules.task.dto.UpdateTaskDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {
  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public ResponseEntity<List<Task>> getUserTasks(Authentication authentication) {
    String username = authentication.getName();
    List<Task> tasks = taskService.getTasksForUser(username);
    return ResponseEntity.ok(tasks);
  }

  @PostMapping
  public ResponseEntity<Task> createTask(
    @RequestBody @Valid CreateTaskDto createCategoryDto,
    Authentication authentication
  ) {
    String username = authentication.getName();
    Task task = taskService.createTask(createCategoryDto, username);
    return ResponseEntity.ok(task);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Task> updateTask(
    @PathVariable String id,
    @RequestBody @Valid UpdateTaskDto updateTaskDto,
    Authentication authentication
  ) {
    String username = authentication.getName();
    Task task = taskService.updateTask(id, updateTaskDto, username);
    return ResponseEntity.ok(task);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(
    @PathVariable String id,
    Authentication authentication
  ) {
    String username = authentication.getName();
    taskService.deleteTask(id, username);
    return ResponseEntity.noContent().build();
  }
}
