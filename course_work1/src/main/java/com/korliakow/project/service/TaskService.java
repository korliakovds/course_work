package com.korliakow.project.service;

import com.korliakow.project.persistence.model.Task;
import com.korliakow.project.web.dto.CreateTaskDto;
import com.korliakow.project.web.dto.UpdateTaskDto;

import java.util.List;

public interface TaskService {

    Task createTask(CreateTaskDto task);

    List<Task> getProjectTasks(Long projectId);

    Task updateTask(UpdateTaskDto task);

    void deleteTask(Long id);

}
