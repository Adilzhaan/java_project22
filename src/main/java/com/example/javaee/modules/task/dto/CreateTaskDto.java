package com.example.javaee.modules.task.dto;

import com.example.javaee.shared.enums.Priority;
import com.example.javaee.shared.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {
  @NotBlank(message = "Title is required")
  private String title;

  private String description;

  @FutureOrPresent(message = "Due date must not be in the past")
  private Date dueDate;

  private Status status;
  private Priority priority;

  @NotNull(message = "User ID is required")
  private String userId;

  @NotNull(message = "Category ID is required")
  private String categoryId;
}
