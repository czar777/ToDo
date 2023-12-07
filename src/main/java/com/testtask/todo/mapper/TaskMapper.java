package com.testtask.todo.mapper;

import com.testtask.todo.dto.TaskDto;
import com.testtask.todo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task toEntity(TaskDto taskDto);

    void update(TaskDto taskDto, @MappingTarget Task task);
}
