package com.korliakow.project.web.dto;

import com.korliakow.project.persistence.model.ProjectStatus;
import jakarta.validation.constraints.NotBlank;

public record CreateProjectDto(

        @NotBlank
        String name,

        String description,

        ProjectStatus status
) {

}
