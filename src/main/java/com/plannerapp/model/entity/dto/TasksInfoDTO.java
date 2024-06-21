package com.plannerapp.model.entity.dto;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;

import java.time.LocalDate;

public class TasksInfoDTO {

    private long id;

    private Priority priority;

    private LocalDate dueDate;

    private String description;

    public TasksInfoDTO(Task task) {
        this.id = task.getId();
        this.priority = task.getPriority();
        this.dueDate = task.getDueDate();
        this.description = task.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
