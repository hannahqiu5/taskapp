package com.hanqiu.taskapp.services;

import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);

    Task createTask(UUID taskListId, Task task);

    Optional<Task> getTask(UUID taskListId, UUID id);

    Task updateTask(UUID id, Task task);

    void deleteTask(UUID id);
}
