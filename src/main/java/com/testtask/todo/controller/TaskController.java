package com.testtask.todo.controller;

import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "TaskController", description = "Контроллер создает, получает, обновляет и удаляет задачи")
public class TaskController {

    private final TaskService taskService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Создание задачи", description = "Создает новую задачу")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Принимает задачу, которую нужно создать")
    public TaskDto createTask(@RequestBody @Valid TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение задачи", description = "Получает задачу по id")
    public TaskDto getTask(@PathVariable @Parameter(description = "Id задачи") long id) {
        return taskService.getTask(id);
    }

    @GetMapping("/name")
    @Operation(summary = "Получение задачи", description = "Получение задачи по имени")
    public TaskDto getTaskByName(@RequestParam @Parameter(description = "Название задачи") String name) {
        return taskService.getTaskByName(name);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление задачи", description = "Обновляет задачу по id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Принимает задачу, которую нужно создать")
    public void updateTask(@PathVariable @Parameter(description = "Id задачи") long id, @RequestBody @Valid TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет задачу", description = "Удаляет выбранную задачу")
    public void deleteTask(@PathVariable @Parameter(description = "Id задачи") long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/page/{offset}/limit/{limit}")
    @Operation(summary = "Возвращает список задач", description = "Возвращает список задач с пагинацией")
    public List<TaskDto> getAllTasks(@PathVariable @Parameter(description = "Номер страницы") int offset,
                                     @Parameter(description = "Количество задач на странице") @PathVariable int limit) {
        return taskService.getAllTasks(offset, limit);
    }
}
