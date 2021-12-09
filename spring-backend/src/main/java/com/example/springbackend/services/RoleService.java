package com.example.springbackend.services;

import com.example.springbackend.entities.Role;
import com.example.springbackend.exceptions.RoleHasUsersException;
import com.example.springbackend.exceptions.RoleNotFoundException;
import com.example.springbackend.repositories.RoleRepository;
import com.example.springbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Role> searchAll() {
        return roleRepository.findAll();
    }

    public Page<Role> searchAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role searchById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    public List<Role> searchByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }

    public Role insert(Role role) {
        return roleRepository.save(role);
    }

//    public Role insert(RoleDTO roleDTO) {}

    public Role update(Role role, Long id) {
        searchById(id);

        return roleRepository.save(role);
    }

//    public Role update(RoleDTO roleDTO, Long id) {}

    public void deleteById(Long id) {
        Role roleFound = searchById(id);

        if (userRepository.findByRole(roleFound).isEmpty()) {
            roleRepository.delete(roleFound);
        } else {
            throw new RoleHasUsersException(id);
        }
    }
}