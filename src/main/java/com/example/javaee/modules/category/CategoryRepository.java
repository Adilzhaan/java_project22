package com.example.javaee.modules.category;

import com.example.javaee.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  List<Category> findByUserUsername(String username);
}
