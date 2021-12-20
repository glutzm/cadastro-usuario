package com.example.springbackend.api.dto;

import com.example.springbackend.entities.User;
import com.example.springbackend.util.UserInfoConverter;
import com.example.springbackend.validators.PIS;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Convert;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDTO {

    @NotEmpty
    @Size(min = 3, max = 80)
    private String name;

    @NotEmpty
    @Email
    @Size(min = 10, max = 80)
    private String email;

    @NotEmpty
    @CPF(message = "CPF inválido.")
    @Size(min = 11, max = 14)
    @Convert(converter = UserInfoConverter.class)
    private String cpf;

    @NotEmpty
    @PIS
    @Size(min = 11, max = 14)
    @Convert(converter = UserInfoConverter.class)
    private String pis;

    @NotEmpty
    @Size(min = 6, max = 255)
    private String password;

    @NotEmpty
    private Boolean active;


    @Valid
    private AddressDTO address;

    @Valid
    private RoleDTO role;

    public UserInsertDTO(User entity) {
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
