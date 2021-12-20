package com.example.springbackend.api.dto;

import com.example.springbackend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDTO {

    @NotEmpty
    private Long id;
    private String name;

    public RoleDTO(Role entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
