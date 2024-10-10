package com.sds.task.service;

import com.sds.task.dto.TaskDto;
import com.sds.task.model.Task;
import com.sds.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;
    public TaskServiceImpl(TaskRepository taskRepository) {
        super();
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        return convertEntityToDto(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        task.setContents(taskDto.getContents());
        task.setIsDone(taskDto.getIsDone());

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        task.setModifiedDate(time);

        Task updatedTask = taskRepository.save(task);
        return convertEntityToDto(updatedTask);

    }

    @Override
    public TaskDto createTask(TaskDto taskRequestDto) {


        Task task = new Task();


        task.setContents(taskRequestDto.getContents());

        task.setIsDone(false);

        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        task.setCreatedDate(time);

        Task savedTask = taskRepository.save(task);

        return convertEntityToDto(savedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task not found with id: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteTasks() {
        taskRepository.deleteAll();
    }

    private TaskDto convertEntityToDto(Task task) {
        return new TaskDto(task.getId(), task.getContents(), task.getIsDone(), task.getCreatedDate(), task.getModifiedDate());
    }
}
