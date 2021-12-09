package com.example.springbackend.api.controllers;

import com.example.springbackend.api.docs.RootApiControllerDoc;
import com.example.springbackend.api.hateoas.RootModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class RootApiController implements RootApiControllerDoc {

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

        rootModel.add(
                userListLink,
                userInsertLink
        );

        return rootModel;
    }
}
