package com.sds.task;

import com.sds.task.dto.TaskDto;
import com.sds.task.model.Task;
import com.sds.task.repository.TaskRepository;
import com.sds.task.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import com.sds.task.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TaskTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;



    @Test
    @DisplayName("#2 Insert Task Test")
    void createTask() {

        // Given
        TaskDto taskRequestDto = new TaskDto();
        taskRequestDto.setContents("Test task");

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setContents("Test task");
        savedTask.setIsDone(false);
        savedTask.setCreatedDate(LocalDateTime.now());
        savedTask.setModifiedDate(null);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // When
        TaskDto createdTaskDto = taskService.createTask(taskRequestDto);

        // Then
        assertNotNull(createdTaskDto);
        assertEquals(1L, createdTaskDto.getId());
        assertEquals("Test task", createdTaskDto.getContents());
        assertFalse(createdTaskDto.getIsDone());
        assertNotNull(createdTaskDto.getCreatedDate());
        assertNull(createdTaskDto.getModifiedDate());

    }

    @Test
    @DisplayName("#3 Read Task Test")
    void getTask(){

        Long taskId = 1L;

        Task savedTask = new Task();
        savedTask.setId(taskId);
        savedTask.setContents("Test Task");
        savedTask.setIsDone(false);
        savedTask.setCreatedDate(LocalDateTime.now());
        savedTask.setModifiedDate(null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(savedTask));

        TaskDto taskDto = taskService.getTaskById(taskId);

        assertNotNull(taskDto);
        assertEquals(taskId, taskDto.getId());
        assertEquals("Test Task", taskDto.getContents());
        assertFalse(taskDto.getIsDone());
        assertNotNull(taskDto.getCreatedDate());
        assertNull(taskDto.getModifiedDate());

    }

    @Test
    @DisplayName("#4 Update Task Test")
    void updateTask() {
        // update할 Task의 ID
        Long taskId = 1L;

        // 원래 있는 데이터 (가정)
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setContents("Test Task");
        existingTask.setIsDone(false);
        existingTask.setCreatedDate(LocalDateTime.now());
        existingTask.setModifiedDate(null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // update할 내용
        TaskDto taskRequestDto = new TaskDto();
        taskRequestDto.setContents("Updated Task");
        taskRequestDto.setIsDone(true);

        // update된 데이터(가정)
        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setContents("Updated Task");
        updatedTask.setIsDone(true); // 완료된 상태
        updatedTask.setCreatedDate(existingTask.getCreatedDate()); // 생성일은 기존값 유지
        updatedTask.setModifiedDate(LocalDateTime.now()); // 수정된 날짜

        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // update 로직 호출. taskID에 대해서 taskRequestDto 내용으로 업데이트
        TaskDto updatedTaskDto = taskService.updateTask(taskId, taskRequestDto);

        assertNotNull(updatedTaskDto);
        assertEquals(taskId, updatedTaskDto.getId());
        assertEquals("Updated Task", updatedTaskDto.getContents());
        assertTrue(updatedTaskDto.getIsDone()); // 완료 상태로 변경되었는지 확인
        assertEquals(existingTask.getCreatedDate(), updatedTaskDto.getCreatedDate()); // 생성일은 동일한지 확인
        assertNotNull(updatedTaskDto.getModifiedDate()); // 수정일이


    }

    @Test
    @DisplayName("#5 Delete All Tasks Test")
    void deleteAllTasks() {

        // 삭제할 로직
        taskService.deleteTasks();

        // 삭제되고 난 후 assert
        verify(taskRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("#5 Delete One Task Test")
    void deleteTask() {
        Long taskId = 1L;

        // taskId가 1인 task가 있다고 가정...
        when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

}
