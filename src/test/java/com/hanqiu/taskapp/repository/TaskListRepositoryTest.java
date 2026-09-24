package com.hanqiu.taskapp.repository;

import com.hanqiu.taskapp.domain.entities.TaskList;
import com.hanqiu.taskapp.repositories.TaskListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskListRepositoryTest {
    @Autowired
    private TaskListRepository taskListRepository;

    @Test
    void save_shouldPersistTaskList() {
        TaskList taskList = new TaskList();
        taskList.setTitle("test tasklist");
        taskList.setCreated(LocalDateTime.now());
        taskList.setUpdated(LocalDateTime.now());

        TaskList saved = taskListRepository.save(taskList);

        assertNotNull(saved.getId());

        Optional<TaskList> result = taskListRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("test tasklist", result.get().getTitle());
    }

}
