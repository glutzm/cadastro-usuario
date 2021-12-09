package com.example.springbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends EntityNotFoundException {

    public RoleNotFoundException(Long id) {
        super(String.format("Cargo com ID %s não encontrado", id));
    }

    public RoleNotFoundException(String name) {
        super(String.format("Cargo com nome %s não encontrado", name));
    }
}
