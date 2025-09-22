package com.samjsddevelopment.restapitemplate.mapper;

import org.mapstruct.Mapper;

import com.samjsddevelopment.restapitemplate.dto.ParentRequest;
import com.samjsddevelopment.restapitemplate.dto.ParentResponse;
import com.samjsddevelopment.restapitemplate.model.Parent;

import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParentMapper {

    Parent parentRequestToParent(ParentRequest request);
    ParentResponse parentToParentResponse(Parent parent);
}