package com.samjsddevelopment.applicationtracker.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.samjsddevelopment.applicationtracker.dto.UserTaskDto;

import io.camunda.zeebe.client.api.search.response.UserTask;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserTaskMapper {
    @Mapping(source = "key", target = "id")
    UserTaskDto toDto(UserTask userTask);

    List<UserTaskDto> toDtos(List<UserTask> userTaskList);
}
