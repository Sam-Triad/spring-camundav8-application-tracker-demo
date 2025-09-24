package com.samjsddevelopment.applicationtracker.controller;

import java.util.UUID;

import com.samjsddevelopment.applicationtracker.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.applicationtracker.service.ApplicantService;
import com.samjsddevelopment.applicationtracker.service.ApplicationService;
import com.samjsddevelopment.applicationtracker.service.ReviewerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicantService applicantService;
    private final ReviewerService reviewerService;
    private final ApplicationService applicationService;

    @PostMapping("/applicants")
    public ResponseEntity<ApplicantDto> createApplicant(@RequestBody CreateApplicantRequest request) {
        return new ResponseEntity<>(applicantService.createApplicant(request), HttpStatus.CREATED);
    }

    @PostMapping("/reviewers")
    public ResponseEntity<ReviewerDto> createReviewer(@RequestBody CreateReviewerRequest request) {
        return new ResponseEntity<>(reviewerService.createReviewer(request), HttpStatus.CREATED);
    }

    @PostMapping("/applications")
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody CreateApplicationRequest request) {
        return new ResponseEntity<>(applicationService.createApplication(request), HttpStatus.CREATED);
    }

    @PutMapping("/applications/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable UUID id, @RequestBody UpdateApplicationRequest request) {
        return ResponseEntity.ok(applicationService.updateApplication(id, request));
    }

    @PostMapping("/applications/{id}/submit")
    public ResponseEntity<Void> submitApplication(@PathVariable UUID id) {
        applicationService.submitApplication(id);
        return ResponseEntity.accepted().build();
    }
}
