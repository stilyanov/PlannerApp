package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserSession userSession;

    public HomeController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/")
    public String nonLoggedIndex() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String loggedIndex() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        return "home";
    }



}
