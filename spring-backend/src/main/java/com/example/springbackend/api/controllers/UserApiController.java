package com.example.springbackend.api.controllers;

import com.example.springbackend.api.docs.UserApiControllerDoc;
import com.example.springbackend.api.dto.UserInsertDTO;
import com.example.springbackend.api.dto.UserResponse;
import com.example.springbackend.api.dto.UserUpdateDTO;
import com.example.springbackend.api.hateoas.UserAssembler;
import com.example.springbackend.api.util.DTO;
import com.example.springbackend.entities.User;
import com.example.springbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController implements UserApiControllerDoc {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private PagedResourcesAssembler<UserResponse> pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<EntityModel<UserResponse>> searchAll(Pageable pageable) {
        Page<User> users = userService.searchAll(pageable);
        Page<UserResponse> response = users.map(UserResponse::new);
        return pagedResourcesAssembler.toModel(response, userAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<UserResponse> searchById(@PathVariable Long id) {
        UserResponse user = new UserResponse(userService.searchById(id));
        return userAssembler.toModel(user);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<UserResponse> insert(@DTO(UserInsertDTO.class) User user) {
        UserResponse response = new UserResponse(userService.insert(user));
        return userAssembler.toModel(response);
    }

    @PutMapping("/{id}")
    public EntityModel<UserResponse> update(
            @DTO(UserUpdateDTO.class) User user,
            @PathVariable Long id) {
        UserResponse response = new UserResponse(userService.update(user, id));
        return userAssembler.toModel(response);
    }

    @PatchMapping("/{id}")
    public EntityModel<UserResponse> changeAvailability(@PathVariable Long id) {
        UserResponse response = new UserResponse(userService.changeAvailability(id));
        return userAssembler.toModel(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
