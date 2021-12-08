package com.example.springbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "app_roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity{

    @NotNull
    @Size(min = 3, max = 40)
    @Column(nullable = false, length = 40, unique = true)
    public String name;
}
