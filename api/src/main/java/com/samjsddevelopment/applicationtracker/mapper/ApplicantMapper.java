package com.samjsddevelopment.applicationtracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.samjsddevelopment.applicationtracker.dto.ApplicantDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicantRequest;
import com.samjsddevelopment.applicationtracker.model.Applicant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicantMapper {
    Applicant toEntity(CreateApplicantRequest request);
    ApplicantDto toDto(Applicant applicant);
}