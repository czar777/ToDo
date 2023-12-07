package com.testtask.todo.mapper;

import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.entity.Priority;
import com.testtask.todo.entity.Status;
import com.testtask.todo.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    @Spy
    private TaskMapperImpl taskMapper;

    private TaskDto taskDto;
    private Task task;

    @BeforeEach
    void setUp() {
        taskDto = TaskDto.builder()
                .id(1L)
                .name("task")
                .description("description")
                .status(Status.COMPLETED)
                .priority(Priority.HIGH)
                .build();

        task = Task.builder()
                .id(1L)
                .name("task")
                .description("description")
                .status(Status.COMPLETED)
                .priority(Priority.HIGH)
                .build();
    }

    @Test
    void toDto() {
        TaskDto actual = taskMapper.toDto(task);
        assertEquals(taskDto, actual);
    }

    @Test
    void toEntity() {
        Task actual = taskMapper.toEntity(taskDto);
        assertEquals(task, actual);
    }
}