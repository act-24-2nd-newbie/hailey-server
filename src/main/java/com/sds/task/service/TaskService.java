package com.sds.task.service;

import com.sds.task.dto.TaskDto;
import com.sds.task.model.Task;

import java.util.List;

public interface TaskService {
    TaskDto getTaskById(Long taskId);
    List<TaskDto> getAllTasks();

    TaskDto updateTask(Long taskId, TaskDto taskDto);
    TaskDto createTask(TaskDto taskDto);


    void deleteTask(Long taskId);
    void deleteTasks();

}
