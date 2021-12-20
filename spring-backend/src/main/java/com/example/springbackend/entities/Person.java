package com.example.springbackend.entities;

import com.example.springbackend.util.UserInfoConverter;
import com.example.springbackend.validators.PIS;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
    @CPF(message = "CPF inválido.")
    @Size(min = 11, max = 14)
    @Convert(converter = UserInfoConverter.class)
    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @NotNull
    @PIS
    @Size(min = 11, max = 14)
    @Convert(converter = UserInfoConverter.class)
    @Column(nullable = false, length = 11, unique = true)
    private String pis;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Valid
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id_fk", nullable = false)
    private Address address;
}
