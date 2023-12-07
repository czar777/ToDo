package com.testtask.todo.mapper;

import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);

    @Mapping(target = "createdAt", ignore = true)
    void update(TaskDto taskDto, @MappingTarget Task task);

    List<TaskDto> toDtoList(List<Task> tasks);
}
