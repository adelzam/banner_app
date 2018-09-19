package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.service.ErrorAddService;
import com.test_app.banner_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class RegistrationController {

    private final UserService userService;
    private final ErrorAddService errorAddService;

    @Autowired
    public RegistrationController(UserService userService, ErrorAddService errorAddService) {
        this.userService = userService;
        this.errorAddService = errorAddService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          BindingResult bindingResult,
                          Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            errorAddService.writeErrors(bindingResult, model);
        } else {
            if (!userService.addUser(user)) {
                model.put("message", "user exists!");
                return "registration";
            }
        }
        return "redirect:/login";
    }
}
