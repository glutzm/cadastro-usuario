package com.example.springbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(Long id) {
        super(String.format("Usuário com ID %s não encontrado", id));
    }

    public UserNotFoundException(String emailOrName) {
        super(String.format("Usuário %s não encontrado", emailOrName));
    }
}
