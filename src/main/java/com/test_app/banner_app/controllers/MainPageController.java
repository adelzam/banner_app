package com.test_app.banner_app.controllers;

import com.test_app.banner_app.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MainPageController {
    @RequestMapping("/")
    public String getMain() {
        return "main";
    }
    @RequestMapping("/my_page")
    public String myPage(@AuthenticationPrincipal User user, Map<String, Object> model){
        model.put("user", user.getUsername());
        return "myPage";
    }
}
