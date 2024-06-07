package com.korliakow.project.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.persistence.model.ProjectStatus;
import com.korliakow.project.service.ProjectService;
import com.korliakow.project.service.TaskService;
import com.korliakow.project.web.dto.CreateProjectDto;
import com.korliakow.project.web.dto.UpdateProjectDto;
import com.korliakow.project.web.dto.ViewProjectDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    /*
    Create project
    Get all projects
    Update project
    Delete project
     */

    @Test
    void createProject() throws Exception {
        CreateProjectDto createProjectDto = new CreateProjectDto(
                "Test Project",
                "T",
                null
        );
        String projectJson = new ObjectMapper().writeValueAsString(createProjectDto);

        when(projectService.createProject(any(CreateProjectDto.class))).thenReturn(new Project(
                "Test Project",
                "T",
                null
        ));

        mockMvc.perform(post("/api/v1/project")
                .contentType("application/json")
                .content(projectJson))
                .andExpect(status().isCreated());

        verify(projectService).createProject(any(CreateProjectDto.class));
    }

    @Test
    void getAllProjects() throws Exception {
        when(projectService.getAllProjects()).thenReturn(List.of(
                new ViewProjectDto(
                        1L,
                        "Test Project",
                        "T",
                        ProjectStatus.COMPLETED,
                        null
                )
        ));

        mockMvc.perform(get("/api/v1/project"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProject() throws Exception {
        CreateProjectDto createProjectDto = new CreateProjectDto(
                "Test Project",
                "T",
                null
        );
        String projectJson = new ObjectMapper().writeValueAsString(createProjectDto);

        when(projectService.updateProject(any(UpdateProjectDto.class))).thenReturn(new Project(
                "Test Project",
                "T",
                null
        ));

        mockMvc.perform(put("/api/v1/project")
                .contentType("application/json")
                .content(projectJson))
                .andExpect(status().isOk());

        verify(projectService).updateProject(any(UpdateProjectDto.class));
    }

    @Test
    void deleteProject() throws Exception {
        mockMvc.perform(delete("/api/v1/project")
                .param("id", "1"))
                .andExpect(status().isOk());

        verify(projectService).deleteProject(1L);
    }
}
