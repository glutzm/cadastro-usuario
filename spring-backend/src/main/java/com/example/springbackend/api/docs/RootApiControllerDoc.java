package com.example.springbackend.api.docs;

import com.example.springbackend.api.dto.JwtResponse;
import com.example.springbackend.api.dto.UserLoginDTO;
import com.example.springbackend.api.exceptions.ValidateApiError;
import com.example.springbackend.api.hateoas.RootModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Raiz", description = "Controller Raiz")
public interface RootApiControllerDoc {

    @ApiOperation(value = "Listar endpoints do projeto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de endpoints realizada com sucesso.")
    })
    RootModel root();

    @ApiOperation(value = "Autenticação da API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Autenticação realizada com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class)
    })
    JwtResponse auth(UserLoginDTO user);

    @ApiOperation(value = "Revalidação da autenticação por token JWT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Revalidação de token realizada com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class)
    })
    JwtResponse refresh(String refreshToken);
}
