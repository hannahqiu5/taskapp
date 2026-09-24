package com.hanqiu.taskapp.controller;

import com.hanqiu.taskapp.domain.dto.TaskDto;
import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.mappers.TaskMapper;
import com.hanqiu.taskapp.services.TaskService;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final MapperBuilder mapperBuilder;


    public TaskController(TaskService taskService, TaskMapper taskMapper, MapperBuilder mapperBuilder) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.mapperBuilder = mapperBuilder;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {

        return taskService.listTasks(taskListId).stream().map(taskMapper::toDto).toList();
    }

    @PostMapping
    public TaskDto createList(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task task = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(task);
    }

    @GetMapping("/{id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("id") UUID taskId) {
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable("id")UUID uuid, @RequestBody TaskDto taskDto, @PathVariable String task_list_id) {
        Task task = taskMapper.fromDto(taskDto);
        return taskMapper.toDto(taskService.updateTask(uuid,task));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") UUID uuid) {
        taskService.deleteTask(uuid);
    }

}
