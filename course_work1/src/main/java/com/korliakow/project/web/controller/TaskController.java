package com.korliakow.project.web.controller;

import com.korliakow.project.persistence.model.Task;
import com.korliakow.project.service.TaskService;
import com.korliakow.project.web.dto.CreateTaskDto;
import com.korliakow.project.web.dto.UpdateTaskDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody @Valid CreateTaskDto task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getProjectTasks(@RequestParam Long projectId) {
        return taskService.getProjectTasks(projectId);
    }

    @PutMapping
    public Task updateTask(@RequestBody @Valid UpdateTaskDto task) {
        return taskService.updateTask(task);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
    }

}
