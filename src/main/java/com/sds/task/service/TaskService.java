package com.sds.task.service;

import com.sds.task.model.Task;
import com.sds.task.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

import com.sds.task.dto.TaskDto;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String content) {
        Task newtask = new Task();
        newtask.setContents(content);

        LocalDateTime nowUtc = LocalDateTime.now(ZoneOffset.UTC);
        newtask.setCreateDate(nowUtc);
        newtask.setModifiedDate(nowUtc);

        return this.taskRepository.save(newtask);
    }

    public Task getTask(Integer taskId) {
        Optional<Task> optionalTask = this.taskRepository.findById( taskId);
        return optionalTask.orElseThrow(() -> new RuntimeException("Task not found"));

    }

    // 모든 Task 조회
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // 전체 Task 목록 반환
    }

    public Task updateTask(Integer taskId, String content, boolean isDone, String modifiedDate) {
        Optional<Task> optionalTask = this.taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return null; // 또는 예외를 던지지 않고 null을 반환
        }



        ZonedDateTime zonedDateTime = ZonedDateTime.parse(modifiedDate, DateTimeFormatter.ISO_ZONED_DATE_TIME);

        LocalDateTime dateTime = zonedDateTime.toLocalDateTime();


        Task task = optionalTask.get();
        task.setContents(content);
        task.setModifiedDate(dateTime);
        task.setDone(isDone);

        return this.taskRepository.save(task);

    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

}

