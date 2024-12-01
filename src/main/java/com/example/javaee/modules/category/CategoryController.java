package com.example.javaee.modules.category;

import com.example.javaee.entities.Category;
import com.example.javaee.modules.category.dto.CreateCategoryDto;
import com.example.javaee.modules.category.dto.UpdateCategoryDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<Category>> getUserCategories(Authentication authentication) {
    String username = authentication.getName();
    List<Category> categories = categoryService.getCategoriesForUser(username);
    return ResponseEntity.ok(categories);
  }

  @PostMapping
  public ResponseEntity<Category> createCategory(
    @RequestBody @Valid CreateCategoryDto createCategoryDto,
    Authentication authentication
  ) {
    String username = authentication.getName();
    Category category = categoryService.createCategory(createCategoryDto, username);
    return ResponseEntity.ok(category);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Category> updateCategory(
    @PathVariable String id,
    @RequestBody @Valid UpdateCategoryDto updateCategoryDto,
    Authentication authentication
  ) {
    String username = authentication.getName();
    Category category = categoryService.updateCategory(id, updateCategoryDto, username);
    return ResponseEntity.ok(category);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(
    @PathVariable String id,
    Authentication authentication
  ) {
    String username = authentication.getName();
    categoryService.deleteCategory(id, username);
    return ResponseEntity.noContent().build();
  }
}
