package com.example.springbackend.validators;

import com.example.springbackend.entities.User;
import com.example.springbackend.repositories.UserRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    private final UserRepository repository;

    public UserValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Validação CPF
        var userFound = repository.findByCpf(user.getCpf());
        if (userFound.isPresent() && !userFound.get().equals(user)) {
            errors.rejectValue("cpf", "validate.user.cpf.exists");
        }

        // Validação PIS
        userFound = repository.findByPis(user.getPis());
        if (userFound.isPresent() && !userFound.get().equals(user)) {
            errors.rejectValue("pis", "validate.user.pis.exists");
        }

        // Validação E-mail
        userFound = repository.findByEmail(user.getEmail());
        if (userFound.isPresent() && !userFound.get().equals(user)) {
            errors.rejectValue("email", "validate.user.email.exists");
        }
    }
}
