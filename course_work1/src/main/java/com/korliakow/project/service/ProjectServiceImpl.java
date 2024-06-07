package com.korliakow.project.service;

import com.korliakow.project.persistence.dao.ProjectDao;
import com.korliakow.project.persistence.dao.TaskDao;
import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.web.dto.CreateProjectDto;
import com.korliakow.project.web.dto.UpdateProjectDto;
import com.korliakow.project.web.dto.ViewProjectDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;
    private final TaskDao taskDao;

    public ProjectServiceImpl(ProjectDao projectDao, TaskDao taskDao) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
    }

    @Override
    public Project createProject(CreateProjectDto project) {
        return projectDao.save(new Project(project.name(),
                                           project.description(),
                                           project.status()));
    }

    @Override
    public List<ViewProjectDto> getAllProjects() {
        return projectDao.findAll().stream()
                .map(project -> new ViewProjectDto(project.getId(),
                                                   project.getName(),
                                                   project.getDescription(),
                                                   project.getStatus(),
                                                   taskDao.countProjectProgress(project.getId())
                ))
                .toList();
    }

    @Override
    public Project updateProject(UpdateProjectDto project) {
        if (!projectDao.existsById(project.id())) {
            throw new IllegalArgumentException("Project with id " + project.id() + " not found");
        }
        return projectDao.save(new Project(project.id(),
                                           project.name(),
                                           project.description(),
                                           project.status()));
    }

    @Override
    public void deleteProject(Long id) {
        projectDao.deleteById(id);
    }

}
