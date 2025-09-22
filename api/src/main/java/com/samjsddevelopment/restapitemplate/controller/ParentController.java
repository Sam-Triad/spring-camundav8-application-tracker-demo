package com.samjsddevelopment.restapitemplate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.restapitemplate.dto.ChildRequest;
import com.samjsddevelopment.restapitemplate.dto.ParentRequest;
import com.samjsddevelopment.restapitemplate.dto.ParentResponse;
import com.samjsddevelopment.restapitemplate.service.ParentService;

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
