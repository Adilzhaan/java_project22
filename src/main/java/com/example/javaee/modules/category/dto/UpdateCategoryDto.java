package com.example.javaee.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDto {
  @NotBlank(message = "Title is required")
  private String title;
}
