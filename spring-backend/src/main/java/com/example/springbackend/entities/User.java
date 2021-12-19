package com.example.springbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "app_users")
public class User extends Person {

    @JsonIgnore
    @Size(min = 6, max = 255)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private Boolean active = true;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id_fk", nullable = false)
    private Role role;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "create_date_time")
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "update_date_time")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public User(
            Long id,
            String name,
            String email,
            String cpf,
            String pis,
            Address address,
            String password,
            Boolean active,
            Role role) {
        super(id, name, email, cpf, pis, address);
        this.password = password;
        this.active = active;
        this.role = role;
    }

    public User(
            Long id,
            String name,
            String email,
            String cpf,
            String pis,
            Address address,
            Boolean active,
            Role role) {
        super(id, name, email, cpf, pis, address);
        this.active = active;
        this.role = role;
    }
}
