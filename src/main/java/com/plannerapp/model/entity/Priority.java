package com.plannerapp.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PriorityEnum name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "priority")
    private List<Task> tasks;

    public Priority() {
        this.tasks = new ArrayList<>();
    }

    public Priority(PriorityEnum priority, String description) {
        this();

        this.name = priority;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PriorityEnum getName() {
        return name;
    }

    public void setName(PriorityEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
