package com.example.springbackend.validators;

import com.example.springbackend.entities.User;
import com.example.springbackend.services.ViaCepService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CepValidator implements Validator {

    private final ViaCepService viaCepService;

    public CepValidator(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            String cep = user.getAddress().getCep();
            viaCepService.getAddressByCep(cep.replaceAll("[() .-]", ""));
        } catch (RuntimeException e) {
            errors.rejectValue("address.cep", null, e.getMessage());
        }
    }
}
