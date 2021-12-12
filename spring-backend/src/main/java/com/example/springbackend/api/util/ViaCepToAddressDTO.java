package com.example.springbackend.api.util;

import com.example.springbackend.api.dto.AddressDTO;
import com.example.springbackend.dto.ViaCepResponse;
import com.example.springbackend.enums.State;
import org.springframework.stereotype.Component;

@Component
public class ViaCepToAddressDTO {

    public AddressDTO convertToDTO(ViaCepResponse viaCepResponse) {
        return AddressDTO.builder()
                .country("Brasil")
                .state(State.valueOf(viaCepResponse.getUf()))
                .city(viaCepResponse.getLocalidade())
                .cep(viaCepResponse.getCep())
                .street(viaCepResponse.getLogradouro())
                .complement(viaCepResponse.getComplemento())
                .build();
    }
}
