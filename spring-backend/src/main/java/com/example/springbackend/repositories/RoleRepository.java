package com.example.springbackend.repositories;

import com.example.springbackend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<List<Role>> findByNameContainingIgnoreCase(String name);
}
