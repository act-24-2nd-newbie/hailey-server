package com.sds.task.service;

import com.sds.task.model.Task;
import com.sds.task.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.sds.task.dto.TaskDto;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String content) {
        Task newtask = new Task();
        newtask.setContents(content);

        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);
        newtask.setCreatedDate(nowUtc);
        newtask.setModifiedDate(nowUtc);

        newtask.setIsDone(false);
        return this.taskRepository.save(newtask);
    }

    public Task getTask(Long taskId) {
        Optional<Task> optionalTask = this.taskRepository.findById( taskId);
        return optionalTask.orElseThrow(() -> new RuntimeException("Task not found"));

    }

    // 모든 Task 조회
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // 전체 Task 목록 반환
    }

    public Task updateTask(Long taskId, String content, Boolean isDone) {
        Optional<Task> optionalTask = this.taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return null; // 또는 예외를 던지지 않고 null을 반환
        }
        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS);

        Task task = optionalTask.get();

        if(!Objects.equals(content, task.getContents())) task.setModifiedDate(nowUtc);

        task.setContents(content);
        task.setIsDone(isDone);

        return this.taskRepository.save(task);

    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

}

