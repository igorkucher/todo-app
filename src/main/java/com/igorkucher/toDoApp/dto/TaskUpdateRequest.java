package com.igorkucher.toDoApp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TaskUpdateRequest {
    @NotEmpty(message = "The 'name' of task cannot be empty")
    String name;
    @NotEmpty(message = "The 'priority' of task cannot be empty")
    String priority;
    Long stateId;
}
