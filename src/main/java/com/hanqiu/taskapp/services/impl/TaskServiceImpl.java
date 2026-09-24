package com.hanqiu.taskapp.services.impl;

import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.domain.entities.TaskPriority;
import com.hanqiu.taskapp.domain.entities.TaskStatus;
import com.hanqiu.taskapp.repositories.TaskListRepository;
import com.hanqiu.taskapp.repositories.TaskRepository;
import com.hanqiu.taskapp.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an ID");
        }

        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Must have a title");
        }

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID provided!"));

        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                taskList,
                task.getDueDate(),
                TaskStatus.OPEN,
                Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM),
                LocalDateTime.now(),
                LocalDateTime.now()));
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID id) {
        return taskRepository.findByTaskListIdAndId(taskListId, id);
    }

    @Override
    public Task updateTask(UUID id, Task task) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());

        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
