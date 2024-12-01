package com.example.javaee.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDto {
  @NotBlank(message = "Title is required")
  private String title;

  @NotNull(message = "User ID is required")
  private String userId;
}
