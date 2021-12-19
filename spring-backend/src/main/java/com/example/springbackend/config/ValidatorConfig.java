package com.example.springbackend.config;

import com.example.springbackend.repositories.UserRepository;
import com.example.springbackend.services.ViaCepService;
import com.example.springbackend.validators.CepValidator;
import com.example.springbackend.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Bean
    public UserValidator userValidator() {
        return new UserValidator(userRepository);
    }

    @Bean
    public CepValidator cepValidator() {
        return new CepValidator(viaCepService);
    }
}
