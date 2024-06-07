package com.korliakow.project.service;

import com.korliakow.project.persistence.dao.ProjectDao;
import com.korliakow.project.persistence.dao.TaskDao;
import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.persistence.model.Task;
import com.korliakow.project.persistence.model.TaskStatus;
import com.korliakow.project.web.dto.CreateTaskDto;
import com.korliakow.project.web.dto.UpdateTaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TaskServiceImplTest {

    private final TaskDao taskDao = mock(TaskDao.class);
    private final ProjectDao projectDao = mock(ProjectDao.class);

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    /*
    Create a task
    Create a task with wrong project id
    Get project tasks
    Update task
    Update task with wrong id
    Delete task
     */

    @Test
    void createTask() {
        CreateTaskDto createTaskDto = new CreateTaskDto(
                "Test Task",
                "This is a test task",
                1L
        );
        Task expectedTask = new Task(
                "Test Task",
                "This is a test task",
                new Project(1L)
        );
        expectedTask.setId(1L);

        when(projectDao.existsById(createTaskDto.projectId())).thenReturn(true);
        when(taskDao.save(any(Task.class))).thenReturn(expectedTask);

        Task actualTask = taskService.createTask(createTaskDto);

        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getProject().getId(), actualTask.getProject().getId());
    }

    @Test
    void createTask_WrongProjectId() {
        CreateTaskDto createTaskDto = new CreateTaskDto(
                "Test Task",
                "This is a test task",
                1L
        );
        when(projectDao.existsById(createTaskDto.projectId())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(createTaskDto));
    }

    @Test
    void getProjectTasks() {
        Task task = new Task(
                "Test Task",
                "This is a test task",
                new Project(1L)
        );
        when(taskDao.findByProjectId(1L)).thenReturn(
                java.util.List.of(task)
        );

        var actualTasks = taskService.getProjectTasks(1L);

        assertEquals(1, actualTasks.size());
        assertEquals(task.getName(), actualTasks.get(0).getName());
        assertEquals(task.getDescription(), actualTasks.get(0).getDescription());
        assertEquals(task.getProject().getId(), actualTasks.get(0).getProject().getId());
    }

    @Test
    void updateTask() {
        Task task = new Task(
                "Test Task",
                "This is a test task",
                new Project(1L)
        );
        task.setId(1L);
        when(taskDao.findById(1L)).thenReturn(java.util.Optional.of(task));
        when(taskDao.save(any(Task.class))).thenReturn(task);

        Task actualTask = taskService.updateTask(new UpdateTaskDto(
                1L,
                "Updated Task",
                "This is an updated test task",
                TaskStatus.DONE
        ));

        assertEquals(task.getName(), actualTask.getName());
        assertEquals(task.getDescription(), actualTask.getDescription());
        assertEquals(task.getProject().getId(), actualTask.getProject().getId());
        assertEquals(task.getStatus(), actualTask.getStatus());
    }

    @Test
    void updateTask_WrongId() {
        when(taskDao.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(new UpdateTaskDto(
                1L,
                "Updated Task",
                "This is an updated test task",
                TaskStatus.DONE
        )));
    }

    @Test
    void deleteTask() {
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskDao).deleteById(1L);
    }

}
