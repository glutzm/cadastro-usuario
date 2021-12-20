package com.example.springbackend.services;

import com.example.springbackend.entities.User;
import com.example.springbackend.exceptions.PasswordDoesNotMatchException;
import com.example.springbackend.exceptions.UserNotFoundException;
import com.example.springbackend.repositories.UserRepository;
import com.example.springbackend.util.PasswordHelper;
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

    public User searchByCpf(String cpf) {
        return userRepository.findByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException(cpf));
    }

    public User searchByPis(String pis) {
        return userRepository.findByPis(pis)
                .orElseThrow(() -> new UserNotFoundException(pis));
    }

    public List<User> searchByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name)
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
        User foundUser = searchById(id);

        user.setCreateDateTime(foundUser.getCreateDateTime());

        // Verificar se houve troca de senha
        if (user.getPassword() == null) {
            user.setPassword(foundUser.getPassword());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        }

        // Verificar se houve troca de endereço
        if (user.getAddress() == null) {
            user.setAddress(foundUser.getAddress());
        } else {
            user.getAddress().setId(foundUser.getAddress().getId());
            user.getAddress().setCreateDateTime(foundUser.getAddress().getCreateDateTime());
        }

        // Verificar se houve troca de cargo
        if (user.getRole() == null) {
            user.setRole(foundUser.getRole());
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

    public void changePassword(
            String currentPassword,
            String newPassword,
            String email) {
        User user = searchByEmail(email);
        if (PasswordHelper.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new PasswordDoesNotMatchException("Senha atual está incorreta.");
        }
    }

    public void deleteById(Long id) {
        User user = searchById(id);
        userRepository.deleteById(id);
    }
}
