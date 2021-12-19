package com.example.springbackend.validators;

import com.example.springbackend.entities.User;
import com.example.springbackend.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CepValidator implements Validator {

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            String cep = user.getAddress().getCep();
            viaCepService.getAddressByCep(cep);
        } catch (RuntimeException e) {
            errors.rejectValue("cep", null, e.getMessage());
        }
    }
}
