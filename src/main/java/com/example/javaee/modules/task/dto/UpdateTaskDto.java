package com.example.javaee.modules.task.dto;

import com.example.javaee.shared.enums.Priority;
import com.example.javaee.shared.enums.Status;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDto {
  private String id;
  private String title;
  private String description;
  private String dueDate;
  private Status status;
  private Priority priority;
  private String categoryId;
}
