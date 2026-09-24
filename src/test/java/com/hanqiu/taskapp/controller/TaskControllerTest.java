package com.hanqiu.taskapp.controller;

import com.hanqiu.taskapp.domain.dto.TaskDto;
import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.domain.entities.TaskPriority;
import com.hanqiu.taskapp.domain.entities.TaskStatus;
import com.hanqiu.taskapp.mappers.TaskMapper;
import com.hanqiu.taskapp.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskController taskController;

    private UUID taskId;
    private UUID taskListId;
    private Task task;
    private TaskDto dto;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();
        taskListId = UUID.randomUUID();

        task = new Task();
        task.setId(taskId);
        task.setTitle("test");

        dto = new TaskDto(
                taskId,
                "test",
                null,
                null,
                TaskPriority.MEDIUM,
                TaskStatus.OPEN
        );

    }

    @Test
    void listTasks_shouldReturnTasks() {


        when(taskService.listTasks(taskId)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(dto);

        List<TaskDto> result = taskController.listTasks(taskId);
        assertEquals(List.of(dto), result);

        verify(taskMapper).toDto(task);

    }

    @Test
    void createTask_shouldReturnDto() {

    }

    @Test
    void getTask_shouldReturnDto() {

    }


    @Test
    void updateTask_shouldReturnDto() {

    }


    @Test
    void deleteTask_shouldCallService() {

    }

}
