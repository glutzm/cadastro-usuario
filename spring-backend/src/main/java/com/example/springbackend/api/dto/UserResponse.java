package com.example.springbackend.api.dto;

import com.example.springbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse extends RepresentationModel<UserResponse> {

    @Id
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String pis;
    private Boolean active;

    private AddressDTO address;
    private RoleDTO role;

    public UserResponse(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        cpf = entity.getCpf();
        pis = entity.getCpf();
        active = entity.getActive();
        address = new AddressDTO(entity.getAddress());
        role = new RoleDTO(entity.getRole());
    }
}
