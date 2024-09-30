package com.sds.task.service;

import com.sds.task.model.Task;
import com.sds.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public Task getTask(int taskId) {
        Optional<Task> optionalTask = this.taskRepository.findById((long) taskId);
        return optionalTask.orElseThrow(() -> new RuntimeException("Task not found"));
//        return this.taskRepository.findById((long) taskId);
    }

    // 모든 Task 조회
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // 전체 Task 목록 반환
    }

    public Task updateTask(int taskId, String content, boolean isDone) {
        Optional<Task> optionalTask = this.taskRepository.findById((long) taskId);
        optionalTask.orElseThrow(() -> new RuntimeException("Task not found"));
        optionalTask.get().setContents(content);
        optionalTask.get().setModifiedDate(LocalDateTime.now(ZoneOffset.UTC));
        optionalTask.get().setDone(isDone);

        return this.taskRepository.save(optionalTask.get());
    }

//    public void deleteTask(Long id) {
//        taskRepository.deleteById(id);
//    }
}

