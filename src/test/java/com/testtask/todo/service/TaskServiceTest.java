package com.testtask.todo.service;

import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.entity.Task;
import com.testtask.todo.exception.EntityNotFoundException;
import com.testtask.todo.mapper.TaskMapper;
import com.testtask.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    private TaskDto taskDto;
    private TaskDto expected;
    private Task task;
    private Task savedTask;
    private TaskDto savedTaskDto;


    @BeforeEach
    void setUp() {
        taskDto = TaskDto.builder().build();
        task = Task.builder().build();
        savedTask = Task.builder().id(1L).build();
        savedTaskDto = TaskDto.builder().id(1L).build();
        expected = TaskDto.builder().id(1L).build();
    }

    @Test
    void createTask() {
        Mockito.when(taskMapper.toEntity(taskDto)).thenReturn(task);
        Mockito.when(taskRepository.save(task)).thenReturn(savedTask);
        Mockito.when(taskMapper.toDto(savedTask)).thenReturn(savedTaskDto);

        TaskDto actual = taskService.createTask(taskDto);

        assertEquals(expected, actual);
    }

    @Test
    void getTask() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskMapper.toDto(task)).thenReturn(savedTaskDto);

        TaskDto actual = taskService.getTask(1L);

        assertEquals(savedTaskDto, actual);
    }

    @Test
    void getTask_ThrowException() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTask(1L));
    }

    @Test
    void getTaskByName() {
        Mockito.when(taskRepository.findByName("task")).thenReturn(Optional.of(task));
        Mockito.when(taskMapper.toDto(task)).thenReturn(savedTaskDto);

        TaskDto actual = taskService.getTaskByName("task");

        assertEquals(savedTaskDto, actual);
    }

    @Test
    void updateTask() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.updateTask(1L, taskDto);

        Mockito.verify(taskMapper, Mockito.times(1)).update(taskDto, task);
    }

    @Test
    void deleteTask() {
        taskRepository.deleteById(1L);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void getAllTasks() {
        List<Task> tasks = List.of(savedTask);
        PageRequest of = PageRequest.of(0, 10);
        Page<Task> page = new PageImpl<>(tasks);

        Mockito.when(taskRepository.findAll(of)).thenReturn(page);

        taskService.getAllTasks(0, 10);

        Mockito.verify(taskMapper, Mockito.times(1)).toDtoList(tasks);
    }
}