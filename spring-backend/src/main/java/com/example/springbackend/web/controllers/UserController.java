package com.example.springbackend.web.controllers;

import com.example.springbackend.dto.AlertDTO;
import com.example.springbackend.entities.User;
import com.example.springbackend.enums.State;
import com.example.springbackend.exceptions.UserNotFoundException;
import com.example.springbackend.services.RoleService;
import com.example.springbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("admin/users/index");
        mav.addObject("users", userService.searchAll());
        return mav;
    }

    @GetMapping("/{id}/change-availability")
    public ModelAndView changeAvailability(
            @PathVariable Long id,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/users");
        try {
            User user = userService.changeAvailability(id);
            String message = user.getActive()
                    ? "Usuário habilitado com sucesso!"
                    : "Usuário desabilitado com sucesso!";
            attributes.addFlashAttribute(
                    new AlertDTO(
                            message,
                            "alert-success"
                    )
            );
        } catch (UserNotFoundException e) {
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário não pode ser alterado!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView mav = new ModelAndView("admin/users/form");
        mav.addObject("user", new User());
        fillForm(mav);
        return mav;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid User user,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/users");

        if (result.hasErrors()) {
            fillForm(mav);

            mav.setViewName("admin/users/form");
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário não pode ser alterado!",
                            "alert-danger"
                    )
            );
            return mav;
        }

        try {
            userService.insert(user);
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário cadastrado cmo sucesso!",
                            "alert-success"
                    )
            );
        } catch (Exception e) {
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário não pode ser cadastrado!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/users/form");
        mav.addObject("user", userService.searchById(id));
        fillForm(mav);
        return mav;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid User user,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/users");

        if (result.hasErrors()) {
            fillForm(mav);
            mav.setViewName("admin/users/form");
            return mav;
        }

        try {
            userService.update(user, id);
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário atualizado com sucesso!",
                            "alert-success"
                    )
            );
        } catch (Exception e) {
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário não pode ser atualizado!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @GetMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes attributes
    ) {
        try {
            userService.deleteById(id);
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário excluído com sucesso!",
                            "alert-success"
                    )
            );
        } catch (UserNotFoundException e) {
            attributes.addFlashAttribute(
                    new AlertDTO(
                            "Usuário não pode ser excluído!",
                            "alert-danger"
                    )
            );
        }

        return "redirect:/admin/users";
    }

    public void fillForm(ModelAndView mav) {
        mav.addObject("roles", roleService.searchAll());
        mav.addObject("states", State.values());
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<User> search(
            @RequestParam(name = "name", defaultValue = "") String search
    ) {
        return userService.searchByName(search);
    }
}
