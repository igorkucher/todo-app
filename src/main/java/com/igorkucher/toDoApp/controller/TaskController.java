package com.igorkucher.toDoApp.controller;

import com.igorkucher.toDoApp.dto.TaskDto;
import com.igorkucher.toDoApp.dto.TaskRequest;
import com.igorkucher.toDoApp.dto.TaskTransformer;
import com.igorkucher.toDoApp.dto.TaskUpdateRequest;
import com.igorkucher.toDoApp.model.Priority;
import com.igorkucher.toDoApp.model.Task;
import com.igorkucher.toDoApp.service.StateService;
import com.igorkucher.toDoApp.service.TaskService;
import com.igorkucher.toDoApp.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ToDoService todoService;
    private final StateService stateService;

    @Autowired
    public TaskController(TaskService taskService, ToDoService todoService, StateService stateService) {
        this.taskService = taskService;
        this.todoService = todoService;
        this.stateService = stateService;
    }


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @taskServiceImpl.readById(#id).todo.owner.email.equals(authentication.name)")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable(name = "id") Long id) {
        Task task = taskService.readById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        TaskDto result = TaskTransformer.convertToDto(task);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/create/todos/{todo_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskDto> create(@Valid @RequestBody TaskRequest taskRequest, @PathVariable long todo_id) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setPriority(Priority.valueOf(taskRequest.getPriority()));
        task.setState(stateService.getByName("New"));
        task.setTodo(todoService.readById(todo_id));
        Task result = taskService.create(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskTransformer.convertToDto(result));
    }

    @PutMapping("/{task_id}/update/todos/{todo_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskDto> update(@Valid @RequestBody TaskUpdateRequest taskUpdateRequest,
                                          @PathVariable long task_id,
                                          @PathVariable long todo_id) {
        Task task = new Task();
        task.setId(task_id);
        task.setTodo(todoService.readById(todo_id));
        if(taskUpdateRequest.getName()!=null) task.setName(taskUpdateRequest.getName());
        if(taskUpdateRequest.getPriority()!=null) task.setPriority(Priority.valueOf(taskUpdateRequest.getPriority()));
        task.setState(stateService.readById(taskUpdateRequest.getStateId()));
        Task result = taskService.update(task);
        return ResponseEntity.ok().body(TaskTransformer.convertToDto(result));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskDto> delete(@PathVariable(name = "id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

}
