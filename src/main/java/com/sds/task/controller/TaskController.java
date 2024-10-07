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

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, methods = {RequestMethod.GET, RequestMethod.POST})

@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/test")
    public String test() {
        return "hello.......";
    }

    @PostMapping
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

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks); // Task 목록 반환
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable int taskId) {

        return taskService.getTask(taskId);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable int taskId, @RequestBody String requestBody) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(requestBody);
        System.out.println("requestBody : "+requestBody);

        String content = rootNode.get("contents").asText();
        System.out.println("content: "+content);

        boolean isDone = rootNode.get("isDone").asBoolean();
        System.out.println("idDone: "+isDone);

        String modifiedDate = rootNode.get("modifiedDate").asText();
        System.out.println("modifiedDate: "+ modifiedDate);

        return taskService.updateTask(taskId, content, isDone, modifiedDate);
    }

}

