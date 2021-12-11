package com.example.springbackend.api.hateoas;

import com.example.springbackend.api.controllers.UserApiController;
import com.example.springbackend.api.dto.UserResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements SimpleRepresentationModelAssembler<UserResponse> {
    @Override
    public void addLinks(EntityModel<UserResponse> resource) {
        Long id = resource.getContent().getId();

        Link selfLink = linkTo(methodOn(UserApiController.class).searchById(id))
                .withSelfRel()
                .withType("GET")
                .withTitle("Detalhes do usuário");

        Link updateLink = linkTo(methodOn(UserApiController.class).update(null, id))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Atualizar usuário");

        Link changeAvailabilityLink = linkTo(methodOn(UserApiController.class).changeAvailability(id))
                .withSelfRel()
                .withType("PATCH")
                .withTitle("Habilitar/Desabilitar usuário");

        Link deleteLink = linkTo(methodOn(UserApiController.class).deleteById(id))
                .withSelfRel()
                .withType("DELETE")
                .withTitle("Deletar usuário");

        resource.add(
                selfLink,
                updateLink,
                changeAvailabilityLink,
                deleteLink
        );
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<UserResponse>> resources) {
        Link selfLink = linkTo(methodOn(UserApiController.class).searchAll(null))
                .withSelfRel()
                .withType("GET")
                .withTitle("Buscar todos os usuários");

        resources.add(selfLink);
    }
}
