package com.example.springbackend.web.exceptions;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice(annotations = Controller.class)
public class WebExceptionHandler implements ErrorViewResolver {
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView mav = new ModelAndView("errors/exceptions");

        mav.addObject("status", status.value());

        switch (status.value()) {
            case 400 -> {
                mav.addObject("causeTitle", "Requisição mal feita.");
                mav.addObject("message", "O servidor não pôde processar a requisição devido a alguma coisa que foi entendida como um erro do cliente!");
                mav.addObject("cause", "A url para página '" + model.get("path") + "' não existe.");
                mav.addObject("cssClass", "text-warning");
            }
            case 404 -> {
                mav.addObject("causeTitle", "Página não encontrada.");
                mav.addObject("message", "A página que você procura não existe!");
                mav.addObject("cause", "A url para página '" + model.get("path") + "' não existe.");
                mav.addObject("cssClass", "text-warning");
            }
            case 500 -> {
                mav.addObject("causeTitle", "Erro interno no servidor.");
                mav.addObject("message", "Alguma coisa deu errado.");
                mav.addObject("cause", "Ocorreu um erro inesperado, tente mais tarde.");
                mav.addObject("cssClass", "text-danger");
            }
            default -> {
                mav.addObject("causeTitle", "Erro interno no servidor.");
                mav.addObject("message", "Contate o administrador.");
                mav.addObject("cause", "Ocorreu um erro não esperado, tente mais tarde.");
                mav.addObject("cssClass", "text-danger");
            }
        }

        return mav;
    }
}
