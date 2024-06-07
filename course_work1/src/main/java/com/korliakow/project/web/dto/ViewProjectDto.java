package com.korliakow.project.web.dto;

import com.korliakow.project.persistence.model.ProjectStatus;

public record ViewProjectDto (
        Long id,
        String name,
        String description,
        ProjectStatus status,
        Double progress
)
{

}
