package com.example.springbackend.web.controllers;

import com.example.springbackend.dto.AlertDTO;
import com.example.springbackend.entities.Role;
import com.example.springbackend.exceptions.RoleHasUsersException;
import com.example.springbackend.exceptions.RoleNotFoundException;
import com.example.springbackend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("admin/roles/index");
        mav.addObject("roles", roleService.searchAll());
        return mav;
    }
    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView mav = new ModelAndView("admin/roles/form");
        mav.addObject("role", new Role());
        return mav;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid Role role,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/roles");

        if (result.hasErrors()) {
            mav.setViewName("admin/roles/form");
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser cadastrado!",
                            "alert-danger"
                    )
            );
            return mav;
        }

        try {
            roleService.insert(role);
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo cadastrado com sucesso!",
                            "alert-success"
                    )
            );
        } catch (Exception e) {
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser cadastrado!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/roles/form");
        modelAndView.addObject("role", roleService.searchById(id));
        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid Role role,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes attributes
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/roles");

        if (result.hasErrors()) {
            mav.setViewName("admin/roles/form");
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser atualizado!",
                            "alert-danger"
                    )
            );
            return mav;
        }

        try {
            roleService.update(role, id);
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo atualizado com sucesso!",
                            "alert-success"
                    )
            );
        } catch (RoleNotFoundException e) {
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser atualizado!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            roleService.deleteById(id);
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo excluído com sucesso!",
                            "alert-success"
                    )
            );
        } catch (RoleHasUsersException e) {
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser excluído!",
                            "alert-danger"
                    )
            );
        }

        return "redirect:/admin/roles";
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Role> search(@RequestParam(name = "name", defaultValue = "") String name) {
        return roleService.searchByName(name);
    }
}
