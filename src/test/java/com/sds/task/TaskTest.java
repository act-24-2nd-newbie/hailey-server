package com.sds.task;

import com.sds.task.model.Task;
import com.sds.task.repository.TaskRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ActiveProfiles("test")
@SpringBootTest
class TaskTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("#2 Insert Task Test")
    @Transactional(rollbackFor = Exception.class)
    void createTask() {

        String contents = "test1";
        boolean isDone = false;
        LocalDateTime date = LocalDateTime.now();

        Task task = new Task();
        task.setContents(contents);
        task.setDone(isDone);
        task.setCreateDate(date);
        task.setModifiedDate(date);
        this.taskRepository.save(task);

        Task createdTask = this.taskRepository.findById(task.getId()).orElseThrow(()-> new RuntimeException("Task not found"));
        assertThat(createdTask.getContents(), is(contents));
        assertThat(createdTask.isDone(), is(isDone));
        assertThat(createdTask.getCreateDate(), is(date));
        assertThat(createdTask.getModifiedDate(), is(date));
        
    }

    public Task setUp(Integer id) {
        Task task = new Task();
        task.setId(id);
        task.setContents("test");
        task.setDone(false);
        task.setCreateDate(LocalDateTime.now());
        task.setModifiedDate(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Test
    @DisplayName("#3 Read Task Test")
    @Transactional(rollbackFor = Exception.class)
    void readTask(){
        Task task =  setUp(10);
        this.taskRepository.flush();

        Task readTask = this.taskRepository.findById(task.getId()).orElseThrow(()-> new RuntimeException("Task not found"));
        assertThat(readTask.getContents(), is(task.getContents()));

    }

    @Test
    @DisplayName("#4 Update Task Test")
    @Transactional(rollbackFor = Exception.class)
    void updateTask() {
        Integer taskId = setUp(10).getId();

        Task task = this.taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setContents("test_update");
        this.taskRepository.flush();

        Task updated = this.taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        assertThat(updated.getContents(), is("test_update"));
    }

    @Test
    @DisplayName("#5 Delete Task Test")
    @Transactional(rollbackFor = Exception.class)
    void deleteTask() {
        Integer taskId = setUp(11).getId();

        Task task = this.taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
        this.taskRepository.flush();

        Optional<Task> deleted = this.taskRepository.findById(taskId);
        assertThat(deleted.isPresent(), is(false));
    }

    @Test
    @DisplayName("#5 Delete All Tasks Test")
    @Transactional(rollbackFor = Exception.class)
    void deleteAllTasks() {
        setUp(11);
        setUp(12);
        this.taskRepository.flush();

        this.taskRepository.deleteAll();
        this.taskRepository.flush();

        List<Task> tasks = this.taskRepository.findAll();
        assertThat( tasks.size(), is(0));
    }

}
