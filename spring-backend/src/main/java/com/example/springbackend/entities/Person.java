package com.example.springbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 80)
    @Column(nullable = false, length = 80)
    private String name;

    @NotNull
    @Email
    @Size(min = 10, max = 80)
    @Column(nullable = false, length = 80, unique = true)
    private String email;

    @NotNull
    @CPF
    @Size(min = 11, max = 11)
    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    //TODO criar custom validador PIS
    @NotNull
    @Size(min = 11, max = 11)
    @Column(nullable = false, length = 11, unique = true)
    private String pis;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Valid
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id_fk", nullable = false)
    private Address address;
}
