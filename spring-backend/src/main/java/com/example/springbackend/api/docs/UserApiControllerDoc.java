package com.example.springbackend.api.docs;

import com.example.springbackend.api.annotations.ApiPageable;
import com.example.springbackend.api.dto.UserResponse;
import com.example.springbackend.api.dto.UserUpdateDTO;
import com.example.springbackend.api.exceptions.ApiError;
import com.example.springbackend.api.exceptions.ValidateApiError;
import com.example.springbackend.entities.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Usuários", description = "Controller de Usuários")
public interface UserApiControllerDoc {

    @ApiOperation(value = "Listar todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de usuários realizada com sucesso.")
    })
    @ApiPageable
    CollectionModel<EntityModel<UserResponse>> searchAll(@ApiIgnore Pageable pageable);

    @ApiOperation(value = "Buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário encontrado com sucesso."),
            @ApiResponse(code = 404, message = "Usuário não encontrado.", response = ApiError.class)
    })
    EntityModel<UserResponse> searchById(Long id);

    @ApiOperation(value = "Cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuário cadastrado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class)
    })
    EntityModel<UserResponse> insert(User user);

    @ApiOperation(value = "Atualizar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado.", response = ApiError.class)

    })
    EntityModel<UserResponse> update(User user, Long id);

    @ApiOperation(value = "Habilitar/Desabilitar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Usuário não encontrado.", response = ApiError.class)
    })
    EntityModel<UserResponse> changeAvailability(Long id);

    @ApiOperation(value = "Excluir usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Usuário excluído com sucesso."),
            @ApiResponse(code = 404, message = "Usuário não encontrado.", response = ApiError.class)
    })
    ResponseEntity<?> deleteById(Long id);
}
