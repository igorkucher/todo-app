package com.igorkucher.toDoApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskRequest {
    @NotBlank(message = "The 'name' of task cannot be empty")
    String name;
    @NotNull
    String priority;
}
