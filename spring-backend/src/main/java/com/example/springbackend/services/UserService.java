package com.example.springbackend.services;

import com.example.springbackend.entities.Address;
import com.example.springbackend.entities.User;
import com.example.springbackend.exceptions.UserNotFoundException;
import com.example.springbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public Page<User> searchAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User searchById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User searchByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<User> searchByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }

    public User insert(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        // Caso a origem do usuário seja pela API, é necessário buscar o cargo
        if (user.getRole().getName() == null) {
            user.setRole(roleService.searchById(user.getRole().getId()));
        }

        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        User userFound = searchById(id);

        // Verificar se houve troca de senha
        if (user.getPassword() == null) {
            user.setPassword(userFound.getPassword());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        }

        // Verificar se houve troca de endereço
        if (user.getAddress() == null) {
            user.setAddress(userFound.getAddress());
        } else {
            user.getAddress().setId(userFound.getAddress().getId());
        }

        // Verificar se houve troca de cargo
        if (user.getRole() == null) {
            user.setRole(userFound.getRole());
        } else {
            user.setRole(roleService.searchById(user.getRole().getId()));
        }

        user.setId(id);

        return userRepository.save(user);
    }

    public User changeAvailability(Long id) {
        User user = searchById(id);
        user.setActive(!user.getActive());
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        User user = searchById(id);
        userRepository.deleteById(id);
    }
}
