package com.korliakow.project.service;

import com.korliakow.project.persistence.dao.ProjectDao;
import com.korliakow.project.persistence.dao.TaskDao;
import com.korliakow.project.persistence.model.Project;
import com.korliakow.project.persistence.model.ProjectStatus;
import com.korliakow.project.web.dto.CreateProjectDto;
import com.korliakow.project.web.dto.UpdateProjectDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ProjectServiceImplTest {

    private final ProjectDao projectDao = mock(ProjectDao.class);
    private final TaskDao taskDao = mock(TaskDao.class);

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    /*
    Create a project with status
    Create a project without status
    Get all projects
    Update project
    Update project with wrong id
    Delete project
     */

    @Test
    void createProject() {
        CreateProjectDto project =
                new CreateProjectDto("name", "description", ProjectStatus.IN_PROGRESS);

        Project expected = new Project(1L, "name", "description", ProjectStatus.IN_PROGRESS);
        when(projectDao.save(any(Project.class))).thenReturn(expected);

        Project actual = projectService.createProject(project);

        assertEquals(expected, actual);
        verify(projectDao).save(ArgumentMatchers.any(Project.class));
    }

    @Test
    void createProject_NullStatus() {
        CreateProjectDto project = new CreateProjectDto("name", "description", null);

        Project expected = new Project(1L, "name", "description", null);
        when(projectDao.save(any(Project.class))).thenReturn(expected);

        Project actual = projectService.createProject(project);

        assertEquals(expected, actual);
        verify(projectDao).save(ArgumentMatchers.any(Project.class));
    }

    @Test
    void getAllProjects() {
        Project project = new Project(1L, "name", "description", ProjectStatus.IN_PROGRESS);
        when(projectDao.findAll()).thenReturn(List.of(project));

        var actual = projectService.getAllProjects();

        assertEquals(1, actual.size());
        assertEquals(1L, actual.get(0).id());
        assertEquals("name", actual.get(0).name());
        assertEquals("description", actual.get(0).description());
        assertEquals(ProjectStatus.IN_PROGRESS, actual.get(0).status());
        assertEquals(0, actual.get(0).progress());
    }

    @Test
    void updateProject() {
        Project project = new Project(1L, "name", "description", ProjectStatus.IN_PROGRESS);
        when(projectDao.existsById(1L)).thenReturn(true);
        when(projectDao.save(any(Project.class))).thenReturn(project);

        var actual = projectService.updateProject(new UpdateProjectDto(1L,
                                                                       "name",
                                                                       "description",
                                                                       ProjectStatus.IN_PROGRESS));

        assertEquals(project, actual);
        verify(projectDao).save(ArgumentMatchers.any(Project.class));
    }

    @Test
    void updateProject_WrongId_ThrowException() {
        when(projectDao.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                     () -> projectService.updateProject(new UpdateProjectDto(1L,
                                                                             "name",
                                                                             "description",
                                                                             ProjectStatus.IN_PROGRESS)));
        verify(projectDao, Mockito.never()).save(ArgumentMatchers.any(Project.class));
    }

    @Test
    void deleteProject() {
        projectService.deleteProject(1L);

        verify(projectDao).deleteById(1L);
    }


}
