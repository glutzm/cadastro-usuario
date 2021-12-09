package com.example.springbackend.api.dto;

import com.example.springbackend.entities.Address;
import com.example.springbackend.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDTO {

    private Long id;
    private String country;
    private State state;
    private String city;
    private String cep;
    private String street;
    private String number;
    private String complement;

    public AddressDTO(Address entity) {
        id = entity.getId();
        country = entity.getCountry();
        state = entity.getState();
        city = entity.getCity();
        cep = entity.getCep();
        street = entity.getStreet();
        number = entity.getNumber();
        complement = entity.getComplement();
    }
}
