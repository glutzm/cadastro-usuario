package com.example.springbackend.api.dto;

import com.example.springbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {

    @Id
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String pis;
    private String password;
    private Boolean active;

    private AddressDTO address;
    private RoleDTO role;

    // TODO: prever datas de registros para auditoria
//    @JsonIgnore
//    private final LocalDateTime editedAt = LocalDateTime.now();

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
