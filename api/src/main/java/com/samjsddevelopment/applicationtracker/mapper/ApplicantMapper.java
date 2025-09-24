package com.samjsddevelopment.applicationtracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.samjsddevelopment.applicationtracker.dto.ApplicantDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicantRequest;
import com.samjsddevelopment.applicationtracker.model.Applicant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicantMapper {

    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "id", ignore = true)
    Applicant toEntity(CreateApplicantRequest request);

    ApplicantDto toDto(Applicant applicant);
}