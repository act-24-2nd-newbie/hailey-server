package com.sds.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.sds.task.dto.TaskDto;
import com.sds.task.model.Task;
import com.sds.task.service.TaskService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ModelMapper modelMapper;
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        super();
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        System.out.println("create Controller");
        return ResponseEntity.ok(taskService.createTask(taskDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }


    // ApiResponse 만들어서 반환값 만들어보기
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteTasks();
        return ResponseEntity.noContent().build();

    }
    
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable long taskId, @RequestBody TaskDto taskDto) {

        return ResponseEntity.ok(taskService.updateTask(taskId, taskDto));
    }

}

