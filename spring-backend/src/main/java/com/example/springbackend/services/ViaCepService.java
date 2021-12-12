package com.example.springbackend.services;

import com.example.springbackend.api.dto.AddressDTO;
import com.example.springbackend.api.util.ViaCepToAddressDTO;
import com.example.springbackend.dto.ViaCepResponse;
import com.example.springbackend.exceptions.CepNotFoundException;
import com.example.springbackend.exceptions.InvalidCepException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    @Autowired
    private ViaCepToAddressDTO viaCepToAddressDTO;

    public AddressDTO getAddressByCep(String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);

        RestTemplate httpClient = new RestTemplate();
        ResponseEntity<ViaCepResponse> response;

        try {
            response = httpClient.getForEntity(url, ViaCepResponse.class);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new InvalidCepException("CEP inválido!");
        }

        if (response.hasBody() && response.getBody().getCep() == null) {
            throw new CepNotFoundException("CEP não encontrado!");
        }

        if (response.hasBody()) {
            return viaCepToAddressDTO.convertToDTO(response.getBody());
        } else {
            return null;
        }
    }
}
