package com.korliakow.project.web.dto;

import com.korliakow.project.persistence.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskDto(

        @NotNull(message = "Task id must not be null")
        Long id,

        @NotBlank(message = "Task name must not be blank")
        String name,

        String description,

        TaskStatus status
) {

}
