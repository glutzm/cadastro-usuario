package com.example.springbackend.api.controllers;

import com.example.springbackend.api.docs.UserApiControllerDoc;
import com.example.springbackend.api.dto.UserDTO;
import com.example.springbackend.entities.User;
import com.example.springbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController implements UserApiControllerDoc {

    @Autowired
    private UserService userService;

    public CollectionModel<EntityModel<User>> searchAll(Pageable pageable) {
        return null;
    }

    public EntityModel<User> searchById(Long id) {
        return null;
    }

    public EntityModel<User> insert(UserDTO userDTO) {
        return null;
    }

    public EntityModel<User> update(UserDTO userDTO, Long id) {
        return null;
    }

    public EntityModel<User> changeAvailability(Long id) {
        return null;
    }

    public ResponseEntity<?> deleteById(Long id) {
        return null;
    }
}
