package com.example.springbackend.web.controllers;

import com.example.springbackend.dto.AlertDTO;
import com.example.springbackend.dto.ChangePasswordDTO;
import com.example.springbackend.entities.User;
import com.example.springbackend.exceptions.PasswordDoesNotMatchException;
import com.example.springbackend.exceptions.UserNotFoundException;
import com.example.springbackend.services.RoleService;
import com.example.springbackend.services.UserService;
import com.example.springbackend.util.FillUserForm;
import com.example.springbackend.validators.CepValidator;
import com.example.springbackend.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FillUserForm fillUserForm;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CepValidator cepValidator;

    @Autowired
    private UserValidator userValidator;

    @InitBinder("user")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(cepValidator, userValidator);
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("admin/users/index");
        mav.addObject("users", userService.searchAll());
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView details(Principal principal) {
        ModelAndView mav = new ModelAndView("user/profile");
        User user = userService.searchByEmail(principal.getName());
        mav.addObject("user", user);
        fillUserForm.fillForm(mav);
        mav.addObject("passwordForm", new ChangePasswordDTO());
        return mav;
    }

    @PostMapping("/change-password")
    public String changePassword(
            @Valid ChangePasswordDTO form,
            BindingResult result,
            Principal principal,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Não foi possível alterar a senha.",
                            "alert-danger"
                    )
            );
            return "redirect:/admin/users/profile";
        }

        try {
            userService.changePassword(form.getCurrentPassword(), form.getNewPassword(), principal.getName());
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Senha alterada com sucesso!",
                            "alert-success"
                    )
            );
        } catch (PasswordDoesNotMatchException e) {
            attributes.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            e.getMessage(),
                            "alert-danger"
                    )
            );
        }
        return "redirect:/admin/users/profile";
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
                    "alert",
                    new AlertDTO(
                            message,
                            "alert-success"
                    )
            );
        } catch (UserNotFoundException e) {
            attributes.addFlashAttribute(
                    "alert",
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
        fillUserForm.fillForm(mav);
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
            fillUserForm.fillForm(mav);

            mav.setViewName("admin/users/form");
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Usuário não pode ser cadastrado!",
                            "alert-danger"
                    )
            );
            return mav;
        }

        try {
            userService.insert(user);
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

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("admin/users/form");
        mav.addObject("user", userService.searchById(id));
        fillUserForm.fillForm(mav);
        return mav;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid User user,
            BindingResult result,
            @PathVariable Long id
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/users");

        if (result.hasErrors()) {
            fillUserForm.fillForm(mav);
            mav.setViewName("admin/users/form");
            mav.addObject("alert",
                    new AlertDTO(
                            "Usuário não pode ser atualizado!",
                            "alert-danger"
                    ));
            return mav;
        }

        try {
            userService.update(user, id);
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Usuário atualizado com sucesso!",
                            "alert-success"
                    )
            );
        } catch (Exception e) {
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Usuário não pode ser atualizado!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @PostMapping("/profile")
    public ModelAndView update(
            @Valid User user,
            BindingResult result
    ) {
        ModelAndView mav = new ModelAndView("redirect:/admin/users/profile");

        if (result.hasErrors()) {
            fillUserForm.fillForm(mav);
            mav.setViewName("user/profile");
            mav.addObject("alert",
                    new AlertDTO(
                            "Não foi possível atualizar seus dados!",
                            "alert-danger"
                    ));
            mav.addObject("passwordForm", new ChangePasswordDTO());
            return mav;
        }
        Long id = user.getId();

        try {
            userService.update(user, id);
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Seus dados foram atualizado com sucesso!",
                            "alert-success"
                    )
            );
        } catch (Exception e) {
            mav.addObject(
                    "alert",
                    new AlertDTO(
                            "Não foi possível atualizar seus dados!",
                            "alert-danger"
                    )
            );
        }

        return mav;
    }

    @GetMapping({"/{id}/delete", "/profile/delete"})
    public String delete(
            @PathVariable(required = false) Long id,
            RedirectAttributes attributes,
            Principal principal,
            HttpServletRequest request
    ) throws ServletException {
        String action = "redirect:/admin/users";
        if (id == null) {
            id = userService.searchByEmail(principal.getName()).getId();
            action = "redirect:/";
            request.logout();
        }
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

        return action;
    }


    // Caso queira implementar opção para o usuário se auto desabilitar
    @GetMapping("/profile/disable")
    public String profileDisable(Principal principal) {
        User user = userService.searchByEmail(principal.getName());

        userService.changeAvailability(user.getId());

        return "redirect:/login";
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<User> search(
            @RequestParam(name = "name", defaultValue = "") String search
    ) {
        return userService.searchByName(search);
    }
}
