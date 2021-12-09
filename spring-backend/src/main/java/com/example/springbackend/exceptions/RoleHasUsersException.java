package com.example.springbackend.exceptions;

public class RoleHasUsersException extends RuntimeException {

    public RoleHasUsersException(Long id) {
        super(String.format("Cargo com ID %s possui usuários vinculados.", id));
    }
}
