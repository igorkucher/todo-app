package com.igorkucher.toDoApp.dto;

import com.igorkucher.toDoApp.model.ToDo;
import com.igorkucher.toDoApp.model.User;

import java.time.LocalDateTime;

public class TodoTransformer {
    public static TodoDto convertToDto(ToDo toDo) {
        return new TodoDto(
                toDo.getId(),
                toDo.getTitle(),
                toDo.getCreatedAt().toString(),
                toDo.getOwner().getId()
        );
    }

    public static ToDo convertToEntity(TodoDto taskDto, User user) {
        ToDo toDo = new ToDo();
        toDo.setId(taskDto.getId());
        toDo.setTitle(taskDto.getTitle());
        toDo.setCreatedAt(LocalDateTime.parse(taskDto.getCreatedAt()));
        toDo.setOwner(user);
        return toDo;
    }
}
