package com.testtask.todo.service;

import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.entity.Task;
import com.testtask.todo.exception.EntityNotFoundException;
import com.testtask.todo.mapper.TaskMapper;
import com.testtask.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task taskSaved = taskRepository.save(task);
        taskDto = taskMapper.toDto(taskSaved);
        log.info("Task created: {}", taskDto);
        return taskDto;
    }

    @Transactional(readOnly = true)
    public TaskDto getTask(Long id) {
        Task task = getTaskById(id);
        TaskDto taskDto = taskMapper.toDto(task);
        log.info("Task found: {}", taskDto);
        return taskDto;
    }

    @Transactional(readOnly = true)
    public TaskDto getTaskByName(String name) {
        Task task = taskRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn("Task by name {} not found", name);
                    throw new EntityNotFoundException("Task not found");});
        TaskDto taskDto = taskMapper.toDto(task);
        log.info("Task found: {}", taskDto);
        return taskDto;
    }

    @Transactional
    public void updateTask(long id, TaskDto taskDto) {
        Task task = getTaskById(id);
        taskMapper.update(taskDto, task);
        log.info("Task updated: {}", taskDto);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
        log.info("Task deleted: {}", id);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks(Integer offset, Integer limit) {
        List<Task> tasks = taskRepository.findAll(PageRequest.of(offset, limit)).getContent();
        log.info("Tasks found: {}", tasks);
        return tasks;
    }

    private Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task by id {} not found", id);
                    throw new EntityNotFoundException("Task not found");});
    }
}
