package com.example.javaee.modules.category;

import com.example.javaee.entities.Category;
import com.example.javaee.entities.User;
import com.example.javaee.modules.category.dto.CreateCategoryDto;
import com.example.javaee.modules.category.dto.UpdateCategoryDto;
import com.example.javaee.modules.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final UserService userService;

  public CategoryService(CategoryRepository categoryRepository, UserService userService) {
    this.categoryRepository = categoryRepository;
    this.userService = userService;
  }

  public List<Category> getCategoriesForUser(String username) {
    return categoryRepository.findByUserUsername(username);
  }

  public Category getCategoryForUser(String id, String username) {
    return categoryRepository.findById(id)
      .filter(category -> category.getUser().getUsername().equals(username))
      .orElseThrow(() -> new RuntimeException("Category not found or unauthorized access"));
  }

  public Category createCategory(CreateCategoryDto createCategoryDto, String username) {
    User user = userService.findUserByUsername(username);

    Category category = new Category();
    category.setTitle(createCategoryDto.getTitle());
    category.setUser(user);

    return categoryRepository.save(category);
  }

  public Category updateCategory(String id, UpdateCategoryDto updateCategoryDto, String username) {
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Category not found"));

    if (!category.getUser().getUsername().equals(username)) {
      throw new RuntimeException("Unauthorized to update this category");
    }

    category.setTitle(updateCategoryDto.getTitle());

    return categoryRepository.save(category);
  }

  public void deleteCategory(String id, String username) {
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Category not found"));

    if (!category.getUser().getUsername().equals(username)) {
      throw new RuntimeException("Unauthorized to delete this category");
    }

    categoryRepository.delete(category);
  }
}
