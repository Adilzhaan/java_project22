package com.example.javaee.modules.task;

import com.example.javaee.entities.Category;
import com.example.javaee.entities.Task;
import com.example.javaee.entities.User;
import com.example.javaee.modules.category.CategoryService;
import com.example.javaee.modules.task.dto.CreateTaskDto;
import com.example.javaee.modules.task.dto.UpdateTaskDto;
import com.example.javaee.modules.user.UserService;
import com.example.javaee.shared.enums.Priority;
import com.example.javaee.shared.enums.Status;
import com.example.javaee.shared.utils.UpdateUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  public Task getTaskByIdAndUser(String id, String username) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    if (!task.getUser().getUsername().equals(username)) {
      throw new RuntimeException("Unauthorized to access this task");
    }
    return task;
  }

  public Task createTask(CreateTaskDto createTaskDto, String username) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date dueDate;
    try {
      dueDate = formatter.parse(createTaskDto.getDueDate());
      if (dueDate.before(new Date())) {
        throw new RuntimeException("Due date must be today or in the future");
      }
    } catch (ParseException e) {
      throw new RuntimeException("Invalid date format. Please use YYYY-MM-DD");
    }

    User user = userService.findUserByUsername(username);

    Category category = categoryService.getCategoryForUser(createTaskDto.getCategoryId(), username);

    Task task = new Task();
    task.setTitle(createTaskDto.getTitle());
    task.setDescription(createTaskDto.getDescription());
    task.setDueDate(dueDate);
    task.setStatus(createTaskDto.getStatus() != null ? createTaskDto.getStatus() : Status.NOT_STARTED);
    task.setPriority(createTaskDto.getPriority() != null ? createTaskDto.getPriority() : Priority.MEDIUM);
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

    // Manually copy non-null properties
    if (updateTaskDto.getTitle() != null) {
      task.setTitle(updateTaskDto.getTitle());
    }

    if (updateTaskDto.getDescription() != null) {
      task.setDescription(updateTaskDto.getDescription());
    }

    if (updateTaskDto.getDueDate() != null) {
      try {
        // Parse and set the due date
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(updateTaskDto.getDueDate());
        task.setDueDate(dueDate);
      } catch (ParseException e) {
        throw new RuntimeException("Invalid date format. Please use YYYY-MM-DD");
      }
    }

    if (updateTaskDto.getStatus() != null) {
      task.setStatus(updateTaskDto.getStatus());
    }

    if (updateTaskDto.getPriority() != null) {
      task.setPriority(updateTaskDto.getPriority());
    }

    if (updateTaskDto.getCategoryId() != null) {
      Category category = categoryService.getCategoryForUser(updateTaskDto.getCategoryId(), username);
      task.setCategory(category);
    }

    return taskRepository.save(task);
  }


  public void deleteTask(String id, String username) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getUsername().equals(username)) {
      throw new RuntimeException("Unauthorized to delete this task");
    }

    taskRepository.delete(task);
  }
}
