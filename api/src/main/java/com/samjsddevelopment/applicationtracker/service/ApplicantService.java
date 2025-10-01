package com.samjsddevelopment.applicationtracker.service;

import org.springframework.stereotype.Service;
import com.samjsddevelopment.applicationtracker.dto.ApplicantDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicantRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    public ApplicantDto createApplicant(CreateApplicantRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'createApplicant'");
    }
}