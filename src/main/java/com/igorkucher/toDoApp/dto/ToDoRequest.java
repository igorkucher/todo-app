package com.igorkucher.toDoApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ToDoRequest {
    @NotBlank(message = "The 'title' of todo cannot be empty")
    String title;
}
