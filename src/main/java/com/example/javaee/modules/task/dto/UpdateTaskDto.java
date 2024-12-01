package com.example.javaee.modules.task.dto;

import com.example.javaee.shared.enums.Priority;
import com.example.javaee.shared.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDto {
  private String title;
  private String description;

  @FutureOrPresent(message = "Due date must not be in the past")
  private Date dueDate;

  private Status status;
  private Priority priority;
  private String categoryId;
}
