package com.plannerapp.service;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.model.entity.dto.AddTaskDTO;
import com.plannerapp.model.entity.dto.TasksInfoDTO;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PriorityRepository priorityRepository;
    private final UserSession userSession;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, PriorityRepository priorityRepository, UserSession userSession) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.priorityRepository = priorityRepository;
        this.userSession = userSession;
    }

    public boolean addTask(AddTaskDTO taskDTO) {

        if (!userSession.isLoggedIn()) {
            return false;
        }

        Optional<Priority> priority = this.priorityRepository.findByName(taskDTO.getPriority());

        if (priority.isEmpty()) {
            return false;
        }

        Task task = new Task();
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setPriority(priority.get());

        this.taskRepository.save(task);

        return true;
    }

    public List<TasksInfoDTO> findAllTasks() {
        return this.taskRepository
                .findAll()
                .stream()
                .map(TasksInfoDTO::new)
                .collect(Collectors.toList());
    }

    public List<TasksInfoDTO> findUserTasks() {
        return this.taskRepository
                .findByUserId((userSession.id()))
                .stream()
                .map(TasksInfoDTO::new)
                .collect(Collectors.toList());
    }

    public void assignTaskToUser(Long taskId) {
        Task task = this.taskRepository.findById((taskId)).get();

        Optional<User> username = this.userRepository.findByUsername(userSession.username());
        task.setUser(username.get());

        this.taskRepository.save(task);

    }

    public void returnTaskFromUser(Long taskId) {
        Task task = this.taskRepository.findById(taskId).get();

        task.setUser(null);

        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        this.taskRepository.deleteById(taskId);
    }
}
