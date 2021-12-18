package com.example.springbackend.web.controllers;

import com.example.springbackend.dto.AlertDTO;
import com.example.springbackend.entities.User;
import com.example.springbackend.services.UserService;
import com.example.springbackend.util.FillUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/sign-in")
public class SignInController {

    @Autowired
    private UserService service;

    @Autowired
    private FillUserForm fillUserForm;

    @GetMapping
    public ModelAndView signIn() {
        var mav = new ModelAndView("admin/users/form");
        mav.addObject("user", new User());
        fillUserForm.fillForm(mav);
        return mav;
    }

    @PostMapping
    public ModelAndView signIn(
            @Valid User user,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("redirect:/login");

        if (result.hasErrors()) {
            fillUserForm.fillForm(mav);

            mav.setViewName("admin/users/form");
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Cadastro não pode ser concluído!",
                            "alert-danger"
                    )
            );
            return mav;
        }

        try {
            service.insert(user);
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Usuário cadastrado com sucesso!",
                            "alert-success"
                    )
            );
        } catch (Exception e) {
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Usuário não pode ser cadastrado!",
                            "alert-danger"
                    )
            );
        }
        return mav;
    }
}
