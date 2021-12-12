package com.example.springbackend.api.docs;

import com.example.springbackend.api.dto.AddressDTO;
import com.example.springbackend.api.exceptions.ApiError;
import com.example.springbackend.api.exceptions.ValidateApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "ViaCep", description = "Controller de Cep")
public interface ViaCepApiControllerDoc {

    @ApiOperation(value = "Buscar endereço por CEP")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CEP encontrado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class),
            @ApiResponse(code = 404, message = "CEP inválido.", response = ApiError.class),
            @ApiResponse(code = 500, message = "CEP não encontrado.", response = ApiError.class)
    })
    AddressDTO getAddressByCep(String cep);
}
