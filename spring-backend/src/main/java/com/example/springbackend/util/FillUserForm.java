package com.example.springbackend.util;

import com.example.springbackend.enums.State;
import com.example.springbackend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class FillUserForm {

    @Autowired
    private RoleService roleService;

    public void fillForm(ModelAndView mav) {
        mav.addObject("roles", roleService.searchAll());
        mav.addObject("states", State.values());
    }
}
