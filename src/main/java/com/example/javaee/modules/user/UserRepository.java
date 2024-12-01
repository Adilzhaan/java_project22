package com.example.javaee.modules.user;

import com.example.javaee.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findUserByUsername(String username);
}
