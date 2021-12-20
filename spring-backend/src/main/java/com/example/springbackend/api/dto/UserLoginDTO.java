package com.example.springbackend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotEmpty
    @Size(min = 3, max = 255)
    private String login;

    @NotEmpty
    @Size(max = 255)
    private String password;
}
