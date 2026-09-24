package com.hanqiu.taskapp.service;

import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.domain.entities.TaskPriority;
import com.hanqiu.taskapp.domain.entities.TaskStatus;
import com.hanqiu.taskapp.repositories.TaskListRepository;
import com.hanqiu.taskapp.repositories.TaskRepository;
import com.hanqiu.taskapp.services.TaskService;
import com.hanqiu.taskapp.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskServiceImpl taskService;


    @Test
    void getTask_shouldReturnTask() {
        // setup
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("test");

        when(taskRepository.findByTaskListIdAndId(taskListId, taskId))
                .thenReturn(Optional.of(task));

        // act
        Optional<Task> result = taskService.getTask(taskListId, taskId);

        // assert
        assertTrue(result.isPresent());
        assertEquals("test", result.get().getTitle());

    }

    @Test
    void getTask_shouldReturnEmptyWhenTaskDNE() {
        UUID taskListId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();

        when(taskRepository.findByTaskListIdAndId(taskListId, taskId))
                .thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTask(taskListId, taskId);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteTask_shouldCallRepository() {
        UUID taskId = UUID.randomUUID();

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void createTask_shouldRejectTaskWithId() {
        Task task = new Task();
        task.setId(UUID.randomUUID());

        assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(
                        UUID.randomUUID(),
                        task
                )
        );

    }

    @Test
    void createTask_shouldCreateTask() {
        UUID taskListId = UUID.randomUUID();

        TaskList taskList = new TaskList();
        taskList.setId(taskListId);
        taskList.setTitle("test tasklist");

        Task task = new Task();
        task.setTitle("test task");

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(taskListRepository.findById(taskListId))
                .thenReturn(Optional.of(taskList));

        Task result = taskService.createTask(taskListId, task);

        assertEquals("test task", result.getTitle());
        assertEquals(taskList, result.getTaskList());
        assertEquals(TaskStatus.OPEN, result.getStatus());
        assertEquals(TaskPriority.MEDIUM, result.getPriority());
        assertNotNull(result.getCreated());
        assertNotNull(result.getUpdated());

        verify(taskListRepository).findById(taskListId);
        verify(taskRepository).save(any(Task.class));    }

}
