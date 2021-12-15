package com.example.springbackend.repositories;

import com.example.springbackend.entities.Role;
import com.example.springbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<List<User>> findByNameContainingIgnoreCase(String name);
    Optional<User> findByRole(Role role);
}
