package com.hanqiu.taskapp.controller;


import com.hanqiu.taskapp.domain.dto.TaskListDto;
import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.mappers.Impl.TaskListMapperImpl;
import com.hanqiu.taskapp.mappers.TaskListMapper;
import com.hanqiu.taskapp.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists")
public class TaskListController {


    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists().stream().map(taskListMapper::toDto).toList();
    }

    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        TaskList taskList = taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(taskList);

    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id")UUID taskListId) {
        return taskListService.getTaskList(taskListId).map(taskListMapper::toDto);
    }


    @PutMapping("/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID id, @RequestBody TaskListDto taskListDto) {
        TaskList updatedTaskList = taskListService.updateTaskList(id, taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(updatedTaskList);
    }

    @DeleteMapping("/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID id) {
        taskListService.deleteTaskList(id);
    }

}
