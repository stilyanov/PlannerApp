package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.dto.UserLoginDTO;
import com.plannerapp.model.entity.dto.UserRegisterDTO;
import com.plannerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserSession userSession;
    private final UserService userService;

    public UserController(UserSession userSession, UserService userService) {
        this.userSession = userSession;
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegisterDTO registerDTO() {
        return new UserRegisterDTO();
    }

    @ModelAttribute("loginData")
    public UserLoginDTO loginDTO() {
        return new UserLoginDTO();
    }

    @GetMapping("/register")
    public String register() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerData", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";
        }

        boolean success = this.userService.register(registerDTO);

        if (!success) {
            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid UserLoginDTO loginDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/users/login";
        }

        boolean success = this.userService.login(loginDTO);
        if (!success) {
            redirectAttributes.addFlashAttribute("loginData", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
            redirectAttributes.addFlashAttribute("loginError", "Incorrect username or password");
            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        userSession.logout();

        return "redirect:/";
    }

}
