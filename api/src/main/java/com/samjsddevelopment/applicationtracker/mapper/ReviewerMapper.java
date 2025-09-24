package com.samjsddevelopment.applicationtracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.samjsddevelopment.applicationtracker.dto.CreateReviewerRequest;
import com.samjsddevelopment.applicationtracker.dto.ReviewerDto;
import com.samjsddevelopment.applicationtracker.model.Reviewer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applications", ignore = true)
    Reviewer toEntity(CreateReviewerRequest request);
    ReviewerDto toDto(Reviewer reviewer);
}