package com.example.springbackend.api.annotations;

import com.example.springbackend.api.dto.UserInsertDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE,
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "User", value = "User", required = true, dataType = "com.example.springbackend.api.dto.UserInsertDTO", dataTypeClass = UserInsertDTO.class, paramType = "body")
})
public @interface UserInsertModel {
}
