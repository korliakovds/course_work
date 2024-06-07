package com.korliakow.project.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.persistence.model.Task;
import com.korliakow.project.persistence.model.TaskStatus;
import com.korliakow.project.service.TaskService;
import com.korliakow.project.web.dto.CreateTaskDto;
import com.korliakow.project.web.dto.UpdateTaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    /*
    Create task
    Get tasks by project id
    Update task
    Delete task
     */

    @Test
    void createTask() throws Exception {
        CreateTaskDto createTaskDto = new CreateTaskDto(
                "Test Task",
                "This is a test task",
                1L
        );
        String taskJson = new ObjectMapper().writeValueAsString(createTaskDto);

        when(taskService.createTask(any(CreateTaskDto.class))).thenReturn(new Task(
                "Test Task",
                "This is a test task",
                new Project(1L)
        ));

        mockMvc.perform(post("/api/v1/task")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.description").value("This is a test task"));

        verify(taskService).createTask(any(CreateTaskDto.class));
    }

    @Test
    void getTasksByProjectId() throws Exception {
        when(taskService.getProjectTasks(1L)).thenReturn(List.of(
                new Task("Test Task", "Test task", new Project(1L)),
                new Task("Test Task 2", "Test task 2", new Project(1L))
        ));

        mockMvc.perform(get("/api/v1/task")
                                .param("projectId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Test task"))
                .andExpect(jsonPath("$[1].name").value("Test Task 2"))
                .andExpect(jsonPath("$[1].description").value("Test task 2"));

        verify(taskService).getProjectTasks(1L);
    }

    @Test
    void updateTask() throws Exception {
        UpdateTaskDto updateTaskDto = new UpdateTaskDto(
                1L,
                "Test Task",
                "This is a test task",
                TaskStatus.IN_PROGRESS
        );
        String taskJson = new ObjectMapper().writeValueAsString(updateTaskDto);

        when(taskService.updateTask(any(UpdateTaskDto.class))).thenReturn(new Task(
                "Test Task",
                "This is a test task",
                new Project(1L)
        ));

        mockMvc.perform(put("/api/v1/task")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.description").value("This is a test task"));
        
        verify(taskService).updateTask(any(UpdateTaskDto.class));
    }
    
    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/v1/task")
                                .param("id", "1"))
                .andExpect(status().isOk());

        verify(taskService).deleteTask(1L);
    }


}
