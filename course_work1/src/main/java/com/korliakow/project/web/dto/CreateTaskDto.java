package com.korliakow.project.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskDto(

        @NotBlank(message = "Task name must not be blank")
        String name,

        String description,

        @NotNull(message = "Project id must not be null")
        Long projectId
) {

}
