package com.hanqiu.taskapp.controller;


import com.hanqiu.taskapp.domain.dto.TaskListDto;
import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.mappers.Impl.TaskListMapperImpl;
import com.hanqiu.taskapp.mappers.TaskListMapper;
import com.hanqiu.taskapp.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
