package com.example.springbackend.services;

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

    public void insert(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

//    public User insert(UserDTO userDTO){}

    // TODO validar troca de senha
    public void update(User user, Long id) {
        User userFound = searchById(id);
        user.setPassword(userFound.getPassword());
        userRepository.save(user);
    }

//    public User update(UserDTO user, Long id) {}

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
