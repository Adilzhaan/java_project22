package com.example.javaee.modules.task;

import com.example.javaee.entities.Category;
import com.example.javaee.entities.Task;
import com.example.javaee.entities.User;
import com.example.javaee.modules.category.CategoryService;
import com.example.javaee.modules.task.dto.CreateTaskDto;
import com.example.javaee.modules.task.dto.UpdateTaskDto;
import com.example.javaee.modules.user.UserService;
import com.example.javaee.shared.utils.UpdateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final UserService userService;
  private final CategoryService categoryService;

  public TaskService(TaskRepository taskRepository, UserService userService, CategoryService categoryService) {
    this.taskRepository = taskRepository;
    this.userService = userService;
    this.categoryService = categoryService;
  }

  public List<Task> getTasksForUser(String username) {
    return taskRepository.findByUserUsername(username);
  }

  public Task createTask(CreateTaskDto createTaskDto, String username) {
    User user = userService.findUserByUsername(username);

    Category category = categoryService.getCategoryForUser(createTaskDto.getCategoryId(), username);

    Task task = new Task();
    task.setTitle(createTaskDto.getTitle());
    task.setDescription(createTaskDto.getDescription());
    task.setDueDate(createTaskDto.getDueDate());
    task.setStatus(createTaskDto.getStatus());
    task.setPriority(createTaskDto.getPriority());
    task.setUser(user);
    task.setCategory(category);

    return taskRepository.save(task);
  }

  public Task updateTask(String id, UpdateTaskDto updateTaskDto, String username) {
    Task task = taskRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getUsername().equals(username)) {
      throw new RuntimeException("Unauthorized to update this task");
    }

    UpdateUtil.copyNonNullProperties(updateTaskDto, task);

    if (updateTaskDto.getCategoryId() != null) {
      Category category = categoryService.getCategoryForUser(updateTaskDto.getCategoryId(), username);
      task.setCategory(category);
    }

    return taskRepository.save(task);
  }

  public void deleteTask(String id, String username) {
    Task task = taskRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getUsername().equals(username)) {
      throw new RuntimeException("Unauthorized to delete this task");
    }

    taskRepository.delete(task);
  }
}
