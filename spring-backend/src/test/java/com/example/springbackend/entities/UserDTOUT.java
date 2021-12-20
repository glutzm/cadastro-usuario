package com.example.springbackend.entities;

import com.example.springbackend.api.dto.UserResponse;
import com.example.springbackend.enums.State;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class UserDTOUT {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkUserMapping() {
        UserResponse response = new UserResponse();
        User user = new User(
                1L,
                "Admin",
                "admin@app.com",
                "62734592746",
                "62734591746",
                Address.builder()
                        .id(1L)
                        .country("Brasil")
                        .state(State.MG)
                        .city("Governador Valadares")
                        .cep("35043250")
                        .street("Rua Eder da Silveira")
                        .number("982")
                        .build(),
                true,
                Role.builder()
                        .id(1L)
                        .name("Gerente")
                        .build()
        );

        modelMapper.map(user, response);
        assertEquals(response.getName(), user.getName());
        assertEquals(response.getEmail(), user.getEmail());
        assertEquals(response.getCpf(), user.getCpf());
        assertEquals(response.getPis(), user.getPis());
        assertEquals(response.getActive(), user.getActive());
        assertEquals(response.getAddress().getCountry(), user.getAddress().getCountry());
        assertEquals(response.getAddress().getState(), user.getAddress().getState());
        assertEquals(response.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(response.getAddress().getCep(), user.getAddress().getCep());
        assertEquals(response.getAddress().getStreet(), user.getAddress().getStreet());
        assertEquals(response.getAddress().getNumber(), user.getAddress().getNumber());
        assertEquals(response.getAddress().getComplement(), user.getAddress().getComplement());
        assertEquals(response.getRole().getName(), user.getRole().getName());
    }
}
