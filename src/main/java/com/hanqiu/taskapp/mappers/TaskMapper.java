package com.hanqiu.taskapp.mappers;

import com.hanqiu.taskapp.domain.dto.TaskDto;
import com.hanqiu.taskapp.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
