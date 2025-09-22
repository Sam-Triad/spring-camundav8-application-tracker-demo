package com.samjsddevelopment.applicationtracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.applicationtracker.dto.ChildRequest;
import com.samjsddevelopment.applicationtracker.dto.ParentRequest;
import com.samjsddevelopment.applicationtracker.dto.ParentResponse;
import com.samjsddevelopment.applicationtracker.service.ParentService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService service;

    @PostMapping
    public ParentResponse createParent(@RequestBody ParentRequest request) {
        return service.createParent(request);
    }

    @PostMapping("/{parentId}/children")
    public ParentResponse addChild(@PathVariable UUID parentId, @RequestBody ChildRequest request) {
        return service.addChild(parentId, request);
    }

    @GetMapping("/{parentId}")
    public ParentResponse getParent(@RequestParam UUID parentId) {
        return service.getParent(parentId);
    }

}
