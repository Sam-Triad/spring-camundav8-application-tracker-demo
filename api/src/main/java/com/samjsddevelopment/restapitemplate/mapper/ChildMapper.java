package com.samjsddevelopment.restapitemplate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.samjsddevelopment.restapitemplate.dto.ChildRequest;
import com.samjsddevelopment.restapitemplate.model.Child;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChildMapper {
    
    Child childRequestToChild(ChildRequest request);
}
