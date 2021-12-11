package com.example.springbackend.api.dto;

import com.example.springbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String pis;
    private String password;
    private Boolean active;

    private AddressDTO address;
    private RoleDTO role;

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        cpf = entity.getCpf();
        pis = entity.getCpf();
        password = entity.getPassword();
        active = entity.getActive();
        address = new AddressDTO(entity.getAddress());
        role = new RoleDTO(entity.getRole());
    }
}