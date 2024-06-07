package com.korliakow.project.service;

import com.korliakow.project.persistence.dao.ProjectDao;
import com.korliakow.project.persistence.dao.TaskDao;
import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.persistence.model.Task;
import com.korliakow.project.web.dto.CreateTaskDto;
import com.korliakow.project.web.dto.UpdateTaskDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final ProjectDao projectDao;

    public TaskServiceImpl(TaskDao taskDao, ProjectDao projectDao) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
    }

    @Override
    public Task createTask(CreateTaskDto task) {
        if (!projectDao.existsById(task.projectId())) {
            throw new IllegalArgumentException(
                    "Project with id " + task.projectId() + " not found");
        }
        return taskDao.save(new Task(task.name(),
                                     task.description(),
                                     new Project(task.projectId()))
        );
    }

    @Override
    public List<Task> getProjectTasks(Long projectId) {
        return taskDao.findByProjectId(projectId);
    }

    @Override
    public Task updateTask(UpdateTaskDto task) {
        Task taskToUpdate = taskDao.findById(task.id())
                                   .orElseThrow(() -> new IllegalArgumentException(
                                           "Task with id " + task.id() + " not found"));
        taskToUpdate.setName(task.name());
        taskToUpdate.setDescription(task.description());
        if (task.status() != null) {
            taskToUpdate.setStatus(task.status());
        }
        return taskDao.save(taskToUpdate);
    }

    @Override
    public void deleteTask(Long id) {
        taskDao.deleteById(id);
    }

}
