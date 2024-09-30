package com.sds.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.sds.task.model.Task;
import com.sds.task.service.TaskService;
import com.sds.task.dto.TaskDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

//    @GetMapping
//    public List<TaskDto> getAllTasks() {
//        return taskService.getAllTasks();
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
//        TaskDto taskDto = taskService.getTaskById(id);
//        if (taskDto == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(taskDto);
//    }

//    @PostMapping
//    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
//        TaskDto newTask = taskService.createTask(taskDto);
//        return ResponseEntity.ok(newTask);
//    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody String requestBody) throws JsonProcessingException {
        System.out.println(requestBody);

        // 매개변수는 requestBody를 String 형식으로 받아옴 -> JSON으로 변환하여 파싱 작업 필요
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(requestBody);
        JsonNode valueNode = rootNode.get("contents");
        String content = valueNode.asText();
        Task createdTask = taskService.createTask(content);
        return ResponseEntity.ok(createdTask); // 생성된 Task 반환
    }

    @GetMapping("/get")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks); // Task 목록 반환
    }

    @GetMapping("/get/{taskId}")
    public Task getTask(@PathVariable int taskId) {
//        TaskDto taskDto = this.taskService.getTask(taskId);
//
//        return new ResponseEntity<TaskDto>(taskDto, HttpStatus.OK);
        return taskService.getTask(taskId);
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update")
    public Task updateTask(@RequestBody String requestBody) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(requestBody);
        System.out.println("requestBody : "+requestBody);

        int taskId = rootNode.get("id").asInt();
        System.out.println("id: "+taskId);

        String content = rootNode.get("contents").asText();
        System.out.println("content: "+content);

        boolean isDone = rootNode.get("isDone").asBoolean();
        System.out.println("idDone: "+isDone);
        return taskService.updateTask(taskId, content, isDone);
    }

    @GetMapping("/test")
    public String getTask() {
        return "test";
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
//        taskService.deleteTask(id);
//        return ResponseEntity.noContent().build();
//    }
}

