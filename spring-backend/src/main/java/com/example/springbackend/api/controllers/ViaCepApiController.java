package com.example.springbackend.api.controllers;

import com.example.springbackend.api.docs.ViaCepApiControllerDoc;
import com.example.springbackend.api.dto.AddressDTO;
import com.example.springbackend.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cep")
public class ViaCepApiController implements ViaCepApiControllerDoc {

    @Autowired
    private ViaCepService viaCepService;

    @GetMapping
    public AddressDTO getAddressByCep(@RequestParam String cep) {
        return viaCepService.getAddressByCep(cep);
    }
}
