package com.hanqiu.taskapp.mappers.Impl;

import com.hanqiu.taskapp.domain.dto.TaskDto;
import com.hanqiu.taskapp.domain.dto.TaskListDto;
import com.hanqiu.taskapp.domain.entities.Task;
import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.domain.entities.TaskStatus;
import com.hanqiu.taskapp.mappers.TaskListMapper;
import com.hanqiu.taskapp.mappers.TaskMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {
    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList())
                        .orElse(null),
                null,
                null
        );
    }


    @Override
    public TaskListDto toDto(TaskList taskList) {

        List<Task> tasks = Optional.ofNullable(taskList.getTasks())
                .orElse(Collections.emptyList());


        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks()).map(List::size).orElse(0),
        calculateTaskListProgress(tasks),
                tasks.stream()
                        .map(taskMapper::toDto)
                        .toList()
                );
    }


    private Double calculateTaskListProgress(List<Task> tasks) {
        if(null == tasks) {
            return null;
        }
        long closedTaskCount = tasks.stream().filter(task -> TaskStatus.CLOSE == task.getStatus()).count();
    return (double) closedTaskCount / tasks.size();
    }
}
