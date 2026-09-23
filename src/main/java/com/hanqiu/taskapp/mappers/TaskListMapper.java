package com.hanqiu.taskapp.mappers;

import com.hanqiu.taskapp.domain.dto.TaskListDto;
import com.hanqiu.taskapp.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
