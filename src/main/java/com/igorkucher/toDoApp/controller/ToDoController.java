package com.igorkucher.toDoApp.controller;

import com.igorkucher.toDoApp.dto.*;
import com.igorkucher.toDoApp.model.ToDo;
import com.igorkucher.toDoApp.service.TaskService;
import com.igorkucher.toDoApp.service.ToDoService;
import com.igorkucher.toDoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/todos")
public class ToDoController {
    private final ToDoService todoService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public ToDoController(ToDoService todoService, TaskService taskService, UserService userService) {
        this.todoService = todoService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create/users/{owner_id}", method = RequestMethod.POST, produces = {"application/json"})
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<TodoDto> create(@PathVariable("owner_id") Long owner_Id,
                                          @Valid @RequestBody ToDoRequest toDoRequest) {
        ToDo todo = new ToDo();
        todo.setOwner(userService.readById(owner_Id));
        todo.setTitle(toDoRequest.getTitle());
        todo.setCreatedAt(LocalDateTime.now());
        ToDo result = todoService.create(todo);
        return new ResponseEntity<>(TodoTransformer.convertToDto(result), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<List<TaskDto>> read(@PathVariable long id) {
        if (todoService.readById(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<TaskDto> result = taskService.getByTodoId(id)
                .stream().map(TaskTransformer::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{todo_id}/update/users/{owner_id}", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse update(@PathVariable("todo_id") long todoId, @PathVariable("owner_id") Long ownerId,
                                    @RequestParam(value = "owner", required = true) Long owner,
                                    @RequestParam(value = "title", required = true) String title) {
        ToDo todo = todoService.readById(todoId);
        todo.setOwner(userService.readById(owner));
        todo.setTitle(title);
        todo.setCreatedAt(LocalDateTime.now());
        todoService.update(todo);
        return new OperationResponse(String.valueOf(Objects.equals(todoService.readById(todoId).getTitle(), title)));
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @RequestMapping(value = "/{todo_id}/delete/users/{owner_id}", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse delete(@PathVariable("todo_id") long todoId,
                                    @PathVariable("owner_id") long id) {
        todoService.delete(todoId);
        return new OperationResponse(String.valueOf(todoService.readById(todoId) == null));
    }

    @GetMapping("/all/users/{user_id}")
    @PreAuthorize("hasAuthority('ADMIN') or @userServiceImpl.readById(#userId).email.equals(authentication.name)")
    public ResponseEntity<List<TodoDto>> getAll(@PathVariable("user_id") long userId) {
        List<TodoDto> todoDtoList = todoService.getAll().stream()
                .filter(toDo -> toDo.getOwner().getId() == userId).map(TodoTransformer::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }
}
