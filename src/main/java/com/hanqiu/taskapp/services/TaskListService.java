package com.hanqiu.taskapp.services;

import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.domain.entities.TaskStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskLists();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskList(UUID id);

    TaskList updateTaskList(UUID taskListId, TaskList taskList);

    void deleteTaskList(UUID taskListId);
}
