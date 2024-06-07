package com.korliakow.project.service;

import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.web.dto.CreateProjectDto;
import com.korliakow.project.web.dto.UpdateProjectDto;
import com.korliakow.project.web.dto.ViewProjectDto;

import java.util.List;

public interface ProjectService {

    Project createProject(CreateProjectDto project);

    List<ViewProjectDto> getAllProjects();

    Project updateProject(UpdateProjectDto project);

    void deleteProject(Long id);
}
