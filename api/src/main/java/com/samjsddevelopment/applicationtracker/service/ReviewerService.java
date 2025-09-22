package com.samjsddevelopment.applicationtracker.service;

import org.springframework.stereotype.Service;
import com.samjsddevelopment.applicationtracker.dto.CreateReviewerRequest;
import com.samjsddevelopment.applicationtracker.dto.ReviewerDto;
import com.samjsddevelopment.applicationtracker.mapper.ReviewerMapper;
import com.samjsddevelopment.applicationtracker.repository.ReviewerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;
    private final ReviewerMapper reviewerMapper;

    public ReviewerDto createReviewer(CreateReviewerRequest request) {
        var reviewer = reviewerMapper.toEntity(request);
        var savedReviewer = reviewerRepository.save(reviewer);
        return reviewerMapper.toDto(savedReviewer);
    }
}