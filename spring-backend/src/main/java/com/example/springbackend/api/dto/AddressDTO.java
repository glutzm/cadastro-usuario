package com.example.springbackend.api.dto;

import com.example.springbackend.entities.Address;
import com.example.springbackend.enums.State;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDTO {

    private String country;
    private State state;
    private String city;
    private String cep;
    private String street;
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
