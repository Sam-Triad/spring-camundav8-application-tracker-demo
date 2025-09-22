package com.samjsddevelopment.applicationtracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.samjsddevelopment.applicationtracker.dto.ChildRequest;
import com.samjsddevelopment.applicationtracker.model.Child;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChildMapper {
    
    Child childRequestToChild(ChildRequest request);
}
