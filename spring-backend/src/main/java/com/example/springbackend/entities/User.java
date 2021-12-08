package com.example.springbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "app_users")
public class User extends Person{

    @JsonIgnore
    @Size(min = 6, max = 255)
    @Column(nullable = false)
    private String password;

    // TODO validar inicialização padrão
    @NotNull
    @Column(nullable = false)
    private Boolean active = true;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id_fk", nullable = false)
    private Role role;
}
