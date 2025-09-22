package com.samjsddevelopment.applicationtracker.mapper;

import org.mapstruct.Mapper;

import com.samjsddevelopment.applicationtracker.dto.ParentRequest;
import com.samjsddevelopment.applicationtracker.dto.ParentResponse;
import com.samjsddevelopment.applicationtracker.model.Parent;

import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParentMapper {

    Parent parentRequestToParent(ParentRequest request);
    ParentResponse parentToParentResponse(Parent parent);
}