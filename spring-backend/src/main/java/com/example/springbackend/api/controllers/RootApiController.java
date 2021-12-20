package com.example.springbackend.api.controllers;

import com.example.springbackend.api.docs.RootApiControllerDoc;
import com.example.springbackend.api.dto.JwtResponse;
import com.example.springbackend.api.dto.UserLoginDTO;
import com.example.springbackend.api.hateoas.RootModel;
import com.example.springbackend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class RootApiController implements RootApiControllerDoc {

    @Autowired
    private AuthService authService;

    @GetMapping
    public RootModel root() {
        RootModel rootModel = new RootModel();

        Link userListLink = linkTo(methodOn(UserApiController.class).searchAll(null))
                .withRel("users")
                .withType("GET")
                .withTitle("Listar usuários.");
        Link userInsertLink = linkTo(methodOn(UserApiController.class).insert(null))
                .withRel("users")
                .withType("POST")
                .withTitle("Inserir usuários.");

        Link authLink = linkTo(methodOn(RootApiController.class).auth(null))
                .withSelfRel()
                .withType("POST")
                .withTitle("Autenticação da API.");

        Link refreshLink = linkTo(methodOn(RootApiController.class).refresh(null))
                .withSelfRel()
                .withType("POST")
                .withTitle("Revalidação da autenticação por token JWT.");

        rootModel.add(
                userListLink,
                userInsertLink
        );

        return rootModel;
    }

    @PostMapping("/auth")
    public JwtResponse auth(@RequestBody @Valid UserLoginDTO user) {
        return authService.createJwtResponse(user);
    }

    @PostMapping("/refresh/{refreshToken}")
    public JwtResponse refresh(@PathVariable String refreshToken) {
        return authService.createJwtResponse(refreshToken);
    }
}
