package com.example.springbackend.entities;

import com.example.springbackend.enums.State;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(nullable = false, length = 60)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private State state;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String city;

    @NotNull
    @Size(min = 8, max = 8)
    @Column(nullable = false, length = 8)
    private String cep;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String street;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false)
    private String number;

    private String complement;
}
