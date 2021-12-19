package com.example.springbackend.api.dto;

import com.example.springbackend.entities.User;
import com.example.springbackend.util.UserInfoConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Convert;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {

    @Id
    private Long id;

    @NotNull
    @Size(min = 3, max = 80)
    private String name;

    @NotNull
    @Email
    @Size(min = 10, max = 80)
    private String email;

    @NotNull
    @CPF
    @Size(min = 11, max = 11)
    @Convert(converter = UserInfoConverter.class)
    private String cpf;

    @NotNull
    @Size(min = 11, max = 11)
    @Convert(converter = UserInfoConverter.class)
    private String pis;

    @Size(min = 6, max = 255)
    private String password;
    private Boolean active;

    @Valid
    private AddressDTO address;

    @Valid
    private RoleDTO role;

    public UserUpdateDTO(User entity) {
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
