package com.test_app.banner_app.controller;

import com.test_app.banner_app.entity.Role;
import com.test_app.banner_app.entity.User;
import com.test_app.banner_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.put("message", "user exists!");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";
    }
}
