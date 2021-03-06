package com.example.springbackend.config;

import com.example.springbackend.api.dto.UserInsertDTO;
import com.example.springbackend.api.dto.UserUpdateDTO;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    private static final String CONTATO_EMAIL = "gustavo.almatos@gmail.com";
    private static final String CONTATO_URL = "https://lutztecnologia.com.br";
    private static final String CONTATO_NOME = "Lutz Tecnologia";
    private static final String API_VERSAO = "1.0.0";
    private static final String API_DESCRICAO = "API de Cadastro de Usuários";
    private static final String API_TITULO = "Cadastro de Usuários Web";
    private static final String PACOTE_BASE = "com.example.springbackend.api";

    @Bean
    public Docket getDocket(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(UserUpdateDTO.class),
                        typeResolver.resolve(UserInsertDTO.class)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACOTE_BASE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITULO)
                .description(API_DESCRICAO)
                .version(API_VERSAO)
                .contact(new Contact(CONTATO_NOME, CONTATO_URL, CONTATO_EMAIL))
                .build();
    }
}
