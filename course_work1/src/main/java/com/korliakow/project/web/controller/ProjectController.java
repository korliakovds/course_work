package com.korliakow.project.web.controller;

import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.service.ProjectService;
import com.korliakow.project.web.dto.CreateProjectDto;
import com.korliakow.project.web.dto.UpdateProjectDto;
import com.korliakow.project.web.dto.ViewProjectDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid CreateProjectDto project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<ViewProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping
    public Project updateProject(@RequestBody @Valid UpdateProjectDto project) {
        return projectService.updateProject(project);
    }

    @DeleteMapping
    public void deleteProject(Long id) {
        projectService.deleteProject(id);
    }

}
