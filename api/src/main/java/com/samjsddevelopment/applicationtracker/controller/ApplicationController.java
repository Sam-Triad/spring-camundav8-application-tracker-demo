package com.samjsddevelopment.applicationtracker.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.applicationtracker.dto.ApplicationDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicationRequest;
import com.samjsddevelopment.applicationtracker.dto.UpdateApplicationRequest;
import com.samjsddevelopment.applicationtracker.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {
    
    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "Create a new application")
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody CreateApplicationRequest request) {
        return new ResponseEntity<>(applicationService.createApplication(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an application")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable UUID id, @RequestBody UpdateApplicationRequest request) {
        return ResponseEntity.ok(applicationService.updateApplication(id, request));
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "Submit an application for review")
    public ResponseEntity<Void> submitApplication(@PathVariable UUID id) {
        applicationService.submitApplication(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    @Operation(summary = "List applications (paginated)")
    public ResponseEntity<Page<ApplicationDto>> listApplications(Pageable pageable) {
        var page = applicationService.listApplications(pageable);
        return ResponseEntity.ok(page);
    }
}
