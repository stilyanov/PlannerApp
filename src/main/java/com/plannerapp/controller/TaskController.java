package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.dto.AddTaskDTO;
import com.plannerapp.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final UserSession userSession;
    private final TaskService taskService;

    public TaskController(UserSession userSession, TaskService taskService) {
        this.userSession = userSession;
        this.taskService = taskService;
    }

    @ModelAttribute("taskData")
    public AddTaskDTO taskDTO() {
        return new AddTaskDTO();
    }

    @GetMapping("/add")
    public String addTask(){
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        return "task-add";
    }

    @PostMapping("/add")
    public String doAddTask(@Valid AddTaskDTO addTaskDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskData", addTaskDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskData", bindingResult);

            return "redirect:/tasks/add";
        }

        boolean success = taskService.addTask(addTaskDTO);

        if (!success) {
            redirectAttributes.addFlashAttribute("taskData", addTaskDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskData", bindingResult);

            return "redirect:/tasks/add";
        }

        return "redirect:/home";

    }

}
