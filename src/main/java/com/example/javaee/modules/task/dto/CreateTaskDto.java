package com.example.javaee.modules.task.dto;

import com.example.javaee.shared.enums.Priority;
import com.example.javaee.shared.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDto {
  @NotBlank(message = "Title is required")
  private String title;

  private String description;

  @NotBlank(message = "Due date is required")
  private String dueDate;

  private Status status;
  private Priority priority;


  @NotNull(message = "Category ID is required")
  private String categoryId;
}
