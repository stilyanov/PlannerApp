package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.dto.TasksInfoDTO;
import com.plannerapp.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final TaskService taskService;


    public HomeController(UserSession userSession, TaskService taskService) {
        this.userSession = userSession;
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String nonLoggedIndex() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String loggedIndex(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        List<TasksInfoDTO> allTasks = taskService.findAllTasks();

        List<TasksInfoDTO> userTasks = this.taskService.findUserTasks();

        model.addAttribute("allTasks", allTasks);
        model.addAttribute("userTasks", userTasks);

        return "home";
    }

}
