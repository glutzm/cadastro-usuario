package com.example.springbackend.api.dto;

import com.example.springbackend.entities.Address;
import com.example.springbackend.enums.State;
import com.example.springbackend.util.UserInfoConverter;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDTO {

    @NotEmpty
    @Size(min = 3, max = 60)
    private String country;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private State state;

    @NotEmpty
    @Size(min = 3, max = 255)
    private String city;

    @NotEmpty
    @Size(min = 8, max = 10)
    @Convert(converter = UserInfoConverter.class)
    private String cep;

    @NotEmpty
    @Size(min = 3, max = 255)
    private String street;

    @NotEmpty
    @Size(min = 1, max = 20)
    private String number;

    private String complement;

    public AddressDTO(Address entity) {
        country = entity.getCountry();
        state = entity.getState();
        city = entity.getCity();
        cep = entity.getCep();
        street = entity.getStreet();
        number = entity.getNumber();
        complement = entity.getComplement();
    }
}
