package com.example.springbackend.api.docs;

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
}
