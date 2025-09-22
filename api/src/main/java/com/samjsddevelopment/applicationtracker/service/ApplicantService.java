package com.samjsddevelopment.applicationtracker.service;

import org.springframework.stereotype.Service;
import com.samjsddevelopment.applicationtracker.dto.ApplicantDto;
import com.samjsddevelopment.applicationtracker.dto.CreateApplicantRequest;
import com.samjsddevelopment.applicationtracker.mapper.ApplicantMapper;
import com.samjsddevelopment.applicationtracker.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    public ApplicantDto createApplicant(CreateApplicantRequest request) {
        var applicant = applicantMapper.toEntity(request);
        var savedApplicant = applicantRepository.save(applicant);
        return applicantMapper.toDto(savedApplicant);
    }
}