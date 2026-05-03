package com.brapierre.cookbook.repository;

import com.brapierre.cookbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Email-based auth (used by Spring Security & JWT)
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Username checks (used by registration)
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
