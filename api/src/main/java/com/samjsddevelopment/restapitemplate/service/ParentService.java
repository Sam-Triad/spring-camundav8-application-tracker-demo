package com.samjsddevelopment.restapitemplate.service;

import java.util.UUID;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.samjsddevelopment.restapitemplate.dto.ChildRequest;
import com.samjsddevelopment.restapitemplate.dto.ParentRequest;
import com.samjsddevelopment.restapitemplate.dto.ParentResponse;
import com.samjsddevelopment.restapitemplate.mapper.ChildMapper;
import com.samjsddevelopment.restapitemplate.mapper.ParentMapper;
import com.samjsddevelopment.restapitemplate.repository.ParentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentService {
    
    private final ParentRepository repository;
    private final ParentMapper parentMapper = Mappers.getMapper(ParentMapper.class);
    private final ChildMapper childMapper = Mappers.getMapper(ChildMapper.class);

    public ParentResponse createParent(ParentRequest request) {
        var parent = parentMapper.parentRequestToParent(request);
        var saved = repository.saveAndFlush(parent);
        return parentMapper.parentToParentResponse(saved);
    }

    public ParentResponse addChild(UUID parentId, ChildRequest request) {
        var parent = repository.findById(parentId).orElseThrow(RuntimeException::new);
        var child = childMapper.childRequestToChild(request);
        parent.addChild(child);
        var saved = repository.saveAndFlush(parent);
        return parentMapper.parentToParentResponse(saved);
    }

    public ParentResponse getParent(UUID parentId) {
        var parent = repository.findById(parentId).orElseThrow(RuntimeException::new);
        return parentMapper.parentToParentResponse(parent);
    }
}
