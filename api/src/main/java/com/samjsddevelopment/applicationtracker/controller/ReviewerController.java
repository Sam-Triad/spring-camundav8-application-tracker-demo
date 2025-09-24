package com.samjsddevelopment.applicationtracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samjsddevelopment.applicationtracker.dto.CreateReviewerRequest;
import com.samjsddevelopment.applicationtracker.dto.ReviewerDto;
import com.samjsddevelopment.applicationtracker.service.ReviewerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerService reviewerService;

    @PostMapping
    @Operation(summary = "Create a new reviewer")
    public ResponseEntity<ReviewerDto> createReviewer(@RequestBody CreateReviewerRequest request) {
        return new ResponseEntity<>(reviewerService.createReviewer(request), HttpStatus.CREATED);
    }
}
