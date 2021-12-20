package com.example.springbackend.scripts;

import com.example.springbackend.entities.Address;
import com.example.springbackend.entities.Role;
import com.example.springbackend.entities.User;
import com.example.springbackend.enums.State;
import com.example.springbackend.repositories.RoleRepository;
import com.example.springbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // insert default roles
        if (roleRepository.findAll().isEmpty()) {
            Role adminRole = Role.builder().name("Gerente").build();
            Role userRole = Role.builder().name("Desenvolvedor(a)").build();
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
        // insert default user
        if (userRepository.findAll().isEmpty()) {
            User admin = new User(
                    null,
                    "Admin",
                    "admin@app.com",
                    "62734592746",
                    "04486540157",
                    Address.builder()
                            .country("Brasil")
                            .state(State.MG)
                            .city("Governador Valadares")
                            .cep("35043250")
                            .street("Rua Eder da Silveira")
                            .number("982")
                            .build(),
                    passwordEncoder.encode("123456"),
                    true,
                    roleRepository.getById(1L));
            userRepository.save(admin);
        }
    }
}
