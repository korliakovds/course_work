package com.korliakow.project.web.dto;

import com.korliakow.project.persistence.model.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProjectDto(

        @NotNull
        Long id,

        @NotBlank
        String name,

        String description,

        @NotNull
        ProjectStatus status
) {

}
