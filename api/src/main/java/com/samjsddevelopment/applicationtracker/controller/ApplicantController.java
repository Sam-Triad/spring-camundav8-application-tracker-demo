package com.samjsddevelopment.applicationtracker.controller;

import com.samjsddevelopment.applicationtracker.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.applicationtracker.service.ApplicantService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    // @PostMapping
    // @Operation(summary = "Create a new applicant")
    // public ResponseEntity<ApplicantDto> createApplicant(@RequestBody CreateApplicantRequest request) {
    //     return new ResponseEntity<>(applicantService.createApplicant(request), HttpStatus.CREATED);
    // }

}
