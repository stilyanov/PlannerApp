package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.PriorityEnum;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.dto.AllAvailableTasksDTO;
import com.plannerapp.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

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

        List<AllAvailableTasksDTO> allTasks = taskService.findAllTasks();

        model.addAttribute("allTasks", allTasks);

        return "home";
    }

}
